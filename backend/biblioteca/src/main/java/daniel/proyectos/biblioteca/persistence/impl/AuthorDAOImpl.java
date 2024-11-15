package daniel.proyectos.biblioteca.persistence.impl;

import daniel.proyectos.biblioteca.entity.Author;
import daniel.proyectos.biblioteca.persistence.IAuthorDAO;
import daniel.proyectos.biblioteca.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class AuthorDAOImpl implements IAuthorDAO {

    @Autowired
    private AuthorRepository repository;

    @Override
    public List<Author> findAll() {
        return repository.findAll();
    }

    @Override
    public Page<Author> findAll(Pageable page) {
        return repository.findAll(page);
    }

    @Override
    public Optional<Author> findById(Integer id) {
        return repository.findById(id);
    }

    @Override
    public Author save(Author objeto) {
        return repository.save(objeto);
    }

    @Override
    public void deleteById(Integer id) {
        repository.deleteById(id);
    }

    @Override
    public void delete(Author entity) {
        repository.delete(entity);
    }
}
