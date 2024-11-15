package daniel.proyectos.biblioteca.mapper;

import daniel.proyectos.biblioteca.dto.BookDTO;
import daniel.proyectos.biblioteca.entity.Book;
import java.util.List;


public interface BookMapper {
    BookDTO toDTO(Book book);
    Book toEntity(BookDTO bookDTO);
    List<BookDTO> toDTOList(List<Book> books);
    List<Book> toEntityList(List<BookDTO> bookDTOs);
}
