package daniel.proyectos.biblioteca.persistence.impl;

import daniel.proyectos.biblioteca.entity.Genre;
import daniel.proyectos.biblioteca.persistence.IGenreDAO;
import daniel.proyectos.biblioteca.repository.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class GenreDAOImpl implements IGenreDAO {

    @Autowired
    private GenreRepository repository;

    @Override
    public List<Genre> findAll() {
        return repository.findAll();
    }

    @Override
    public Page<Genre> findAll(Pageable page) {
        return repository.findAll(page);
    }

    @Override
    public Optional<Genre> findById(Integer id) {
        return repository.findById(id);
    }

    @Override
    public Genre save(Genre objeto) {
        return repository.save(objeto);
    }

    @Override
    public void deleteById(Integer id) {
        repository.deleteById(id);
    }

    @Override
    public void delete(Genre entity) {
        repository.delete(entity);
    }
}
