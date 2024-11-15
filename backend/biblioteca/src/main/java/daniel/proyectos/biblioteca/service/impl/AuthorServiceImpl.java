package daniel.proyectos.biblioteca.service.impl;

import daniel.proyectos.biblioteca.entity.Author;
import daniel.proyectos.biblioteca.persistence.IAuthorDAO;
import daniel.proyectos.biblioteca.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorServiceImpl implements AuthorService {

    @Autowired
    private IAuthorDAO dao;

    @Override
    public List<Author> findAll() {
        return dao.findAll();
    }

    @Override
    public Page<Author> findAll(Pageable page) {
        return dao.findAll(page);
    }

    @Override
    public Optional<Author> findById(Integer id) {
        return dao.findById(id);
    }

    @Override
    public Author save(Author objeto) {
        return dao.save(objeto);
    }

    @Override
    public void deleteById(Integer id) {
        dao.deleteById(id);
    }

    @Override
    public void delete(Author entity) {
        dao.delete(entity);
    }
}
