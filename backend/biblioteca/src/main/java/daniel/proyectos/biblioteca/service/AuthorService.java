package daniel.proyectos.biblioteca.service;

import daniel.proyectos.biblioteca.entity.Author;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface AuthorService {

    List<Author> findAll();
    Page<Author> findAll(Pageable page);
    Optional<Author> findById(Integer id);
    Author save(Author objeto);
    void deleteById(Integer id);
    void delete(Author entity);
}