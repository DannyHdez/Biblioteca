// src/main/java/daniel/proyectos/biblioteca/service/LibroService.java
package daniel.proyectos.biblioteca.service;

import daniel.proyectos.biblioteca.dto.BookDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface BookService {
    List<BookDTO> findAll();
    Page<BookDTO> findAll(Pageable page);
    Optional<BookDTO> findById(Integer id, int usuarioId);
    BookDTO save(BookDTO objeto);
    void deleteById(Integer id);
    void delete(BookDTO entity);
    List<BookDTO> findAllByUserId(int userId);

}
