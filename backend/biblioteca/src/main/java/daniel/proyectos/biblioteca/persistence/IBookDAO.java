package daniel.proyectos.biblioteca.persistence;

import daniel.proyectos.biblioteca.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface IBookDAO {

    List<Book> findAll();
    Page<Book> findAll(Pageable page);
    Optional<Book> findById(Integer id);
    Book save(Book objeto);
    void deleteById(Integer id);
    void delete(Book entity);
    List<Book> findAllByUserId(Integer userId);
}
