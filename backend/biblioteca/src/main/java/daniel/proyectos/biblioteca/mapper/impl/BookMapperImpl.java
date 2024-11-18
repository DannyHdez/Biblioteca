// src/main/java/daniel/proyectos/biblioteca/mapper/impl/LibroMapperImpl.java
package daniel.proyectos.biblioteca.mapper.impl;

import daniel.proyectos.biblioteca.dto.AuthorInfoDTO;
import daniel.proyectos.biblioteca.dto.EditorialInfoDTO;
import daniel.proyectos.biblioteca.dto.GenreInfoDTO;
import daniel.proyectos.biblioteca.dto.BookDTO;
import daniel.proyectos.biblioteca.entity.Author;
import daniel.proyectos.biblioteca.entity.Editorial;
import daniel.proyectos.biblioteca.entity.Genre;
import daniel.proyectos.biblioteca.entity.Book;
import daniel.proyectos.biblioteca.mapper.BookMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookMapperImpl implements BookMapper {

    @Override
    public BookDTO toDTO(Book book) {
        if (book == null) return null;

        BookDTO dto = new BookDTO();
        dto.setId(book.getId());
        dto.setIsbn(book.getIsbn());
        dto.setTitleEs(book.getTitleEs());
        dto.setTitleEn(book.getTitleEn());
        dto.setTitleCa(book.getTitleCa());
        dto.setPublicationDate(book.getPublicationDate());
        dto.setEditorial(toEditorialInfoDTO(book.getEditorial()));

        // Convert list of authors to AuthorInfoDTO
        dto.setAuthors(book.getAuthors().stream()
                .map(this::toAutorInfoDTO)
                .collect(Collectors.toList()));

        // Convert the list of genres to GenreInfoDTO
        dto.setGenres(book.getGenres().stream()
                .map(this::toGenreInfoDTO)
                .collect(Collectors.toList()));

        return dto;
    }

    @Override
    public Book toEntity(BookDTO bookDTO) {
        if (bookDTO == null) return null;

        Book book = new Book();
        book.setId(bookDTO.getId());
        book.setIsbn(bookDTO.getIsbn());
        book.setTitleEs(bookDTO.getTitleEs());
        book.setTitleEn(bookDTO.getTitleEn());
        book.setTitleCa(bookDTO.getTitleCa());
        book.setPublicationDate(bookDTO.getPublicationDate());

        // Relationships such as publisher, authors and genres are handled in the service or with other methods

        return book;
    }

    @Override
    public List<BookDTO> toDTOList(List<Book> books) {
        return books.stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    public List<Book> toEntityList(List<BookDTO> bookDTOs) {
        return bookDTOs.stream().map(this::toEntity).collect(Collectors.toList());
    }

    // Auxiliary methods to convert Author, Genre and Publisher to their respective DTOs

    private AuthorInfoDTO toAutorInfoDTO(Author author) {
        if (author == null) return null;

        AuthorInfoDTO dto = new AuthorInfoDTO();
        dto.setId(author.getId());
        dto.setName(author.getName());
        dto.setSurnames(author.getSurnames());
        return dto;
    }

    private GenreInfoDTO toGenreInfoDTO(Genre genre) {
        if (genre == null) return null;

        GenreInfoDTO dto = new GenreInfoDTO();
        dto.setId(genre.getId());
        dto.setName(genre.getName());
        return dto;
    }

    private EditorialInfoDTO toEditorialInfoDTO(Editorial editorial) {
        if (editorial == null) return null;

        EditorialInfoDTO dto = new EditorialInfoDTO();
        dto.setId(editorial.getId());
        dto.setName(editorial.getName());
        dto.setLogo(editorial.getLogo());
        return dto;
    }
}
