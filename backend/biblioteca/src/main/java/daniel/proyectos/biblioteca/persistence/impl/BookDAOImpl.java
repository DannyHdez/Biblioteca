package daniel.proyectos.biblioteca.persistence.impl;

import daniel.proyectos.biblioteca.entity.Book;
import daniel.proyectos.biblioteca.persistence.IBookDAO;
import daniel.proyectos.biblioteca.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class BookDAOImpl implements IBookDAO {

    @Autowired
    private BookRepository repository;

    @Override
    public List<Book> findAll() {
        return repository.findAll();
    }

    @Override
    public Page<Book> findAll(Pageable page) {
        return repository.findAll(page);
    }

    @Override
    public Optional<Book> findById(Integer id) {
        return repository.findById(id);
    }

    @Override
    public Book save(Book objeto) {
        return repository.save(objeto);
    }

    @Override
    public void deleteById(Integer id) {
        repository.deleteById(id);
    }

    @Override
    public void delete(Book entity) {
        repository.delete(entity);
    }

    @Override
    public List<Book> findAllByUserId(Integer id) {
        return repository.findByUserId(id);
    }

}
