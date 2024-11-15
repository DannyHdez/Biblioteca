package daniel.proyectos.biblioteca.service;

import daniel.proyectos.biblioteca.entity.Genre;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface GenreService {

    List<Genre> findAll();
    Page<Genre> findAll(Pageable page);
    Optional<Genre> findById(Integer id);
    Genre save(Genre objeto);
    void deleteById(Integer id);
    void delete(Genre entity);
}
