package daniel.proyectos.biblioteca.service.impl;

import daniel.proyectos.biblioteca.entity.Genre;
import daniel.proyectos.biblioteca.persistence.IGenreDAO;
import daniel.proyectos.biblioteca.service.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GenreServiceImpl implements GenreService {

    @Autowired
    private IGenreDAO dao;

    @Override
    public List<Genre> findAll() {
        return dao.findAll();
    }

    @Override
    public Page<Genre> findAll(Pageable page) {
        return dao.findAll(page);
    }

    @Override
    public Optional<Genre> findById(Integer id) {
        return dao.findById(id);
    }

    @Override
    public Genre save(Genre objeto) {
        return dao.save(objeto);
    }

    @Override
    public void deleteById(Integer id) {
        dao.deleteById(id);
    }

    @Override
    public void delete(Genre entity) {
        dao.delete(entity);
    }
}
