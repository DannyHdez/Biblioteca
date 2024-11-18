package daniel.proyectos.biblioteca.service.impl;

import daniel.proyectos.biblioteca.dto.BookDTO;
import daniel.proyectos.biblioteca.dto.CreateBookRequest;
import daniel.proyectos.biblioteca.entity.*;
import daniel.proyectos.biblioteca.mapper.BookMapper;
import daniel.proyectos.biblioteca.persistence.IBookDAO;
import daniel.proyectos.biblioteca.repository.AuthorRepository;
import daniel.proyectos.biblioteca.repository.BookRepository;
import daniel.proyectos.biblioteca.repository.EditorialRepository;
import daniel.proyectos.biblioteca.repository.GenreRepository;
import daniel.proyectos.biblioteca.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private EditorialRepository editorialRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private IBookDAO dao;

    @Autowired
    private BookMapper bookMapper;

    @Override
    public List<BookDTO> findAll() {
        List<Book> books = dao.findAll();
        return bookMapper.toDTOList(books);
    }

    @Override
    public Page<BookDTO> findAll(Pageable page) {
        return dao.findAll(page).map(bookMapper::toDTO);
    }

    @Override
    public Optional<BookDTO> findById(Integer id, int userId) {
        return dao.findById(id)
                .filter(libro -> libro.getUser().getId() == userId)
                .map(bookMapper::toDTO);
    }

    @Override
    public BookDTO save(BookDTO objeto) {
        Book book = bookMapper.toEntity(objeto);
        Book savedBook = dao.save(book);
        return bookMapper.toDTO(savedBook);
    }

    @Override
    public void deleteById(Integer id) {
        dao.deleteById(id);
    }

    @Override
    public void delete(BookDTO entity) {
        Book book = bookMapper.toEntity(entity);
        dao.delete(book);
    }

    @Override
    public List<BookDTO> findAllByUserId(int userId) {

        List<Book> books = dao.findAllByUserId(userId);
        return bookMapper.toDTOList(books);

    }

    private BookDTO convertToDto(Book book) {
        BookDTO dto = new BookDTO();
        dto.setId(book.getId());
        dto.setTitleEs(book.getTitleEs());
        dto.setTitleEn(book.getTitleEn());
        dto.setTitleCa(book.getTitleCa());
        dto.setPublicationDate(book.getPublicationDate());

        return dto;
    }

    @Override
    public BookDTO createBook(CreateBookRequest request, User user) {

        if (bookRepository.existsByIsbn(request.getIsbn())) {
            throw new RuntimeException("A book with this ISBN already exists.");
        }

        Editorial editorial = editorialRepository.findById(request.getEditorialId())
                .orElseThrow(() -> new RuntimeException("Editorial not found"));

        List<Author> authors = authorRepository.findAllById(request.getAuthorIds());
        if (authors.isEmpty()) {
            throw new RuntimeException("Invalid author IDs provided");
        }

        List<Genre> genres = genreRepository.findAllById(request.getGenreIds());
        if (genres.isEmpty()) {
            throw new RuntimeException("Invalid genre IDs provided");
        }

        Book book = new Book();
        book.setIsbn(request.getIsbn());
        book.setTitleEs(request.getTitleEs());
        book.setTitleEn(request.getTitleEn());
        book.setTitleCa(request.getTitleCa());
        book.setPublicationDate(request.getPublicationDate());
        book.setEditorial(editorial);
        book.setAuthors(authors);
        book.setGenres(genres);
        book.setUser(user);

        Book savedBook = bookRepository.save(book);

        return bookMapper.toDTO(savedBook);
    }
}
