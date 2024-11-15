package daniel.proyectos.biblioteca.service.impl;

import daniel.proyectos.biblioteca.dto.BookDTO;
import daniel.proyectos.biblioteca.entity.Book;
import daniel.proyectos.biblioteca.mapper.BookMapper;
import daniel.proyectos.biblioteca.persistence.IBookDAO;
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
}
