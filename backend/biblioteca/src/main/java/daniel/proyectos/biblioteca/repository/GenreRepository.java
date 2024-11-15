package daniel.proyectos.biblioteca.repository;

import daniel.proyectos.biblioteca.entity.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenreRepository extends JpaRepository<Genre, Integer> {
}
