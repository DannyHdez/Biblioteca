package daniel.proyectos.biblioteca.repository;

import daniel.proyectos.biblioteca.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Integer> {
    List<Book> findByUserId(int userId);
    boolean existsByIsbn(String isbn);
}
