package daniel.proyectos.biblioteca.persistence.impl;

import daniel.proyectos.biblioteca.entity.Editorial;
import daniel.proyectos.biblioteca.persistence.IEditorialDAO;
import daniel.proyectos.biblioteca.repository.EditorialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class EditorialDAOImpl implements IEditorialDAO {

    @Autowired
    private EditorialRepository repository;

    @Override
    public List<Editorial> findAll() {
        return repository.findAll();
    }

    @Override
    public Page<Editorial> findAll(Pageable page) {
        return repository.findAll(page);
    }

    @Override
    public Optional<Editorial> findById(Integer id) {
        return repository.findById(id);
    }

    @Override
    public Editorial save(Editorial objeto) {
        return repository.save(objeto);
    }

    @Override
    public void deleteById(Integer id) {
        repository.deleteById(id);
    }

    @Override
    public void delete(Editorial entity) {
        repository.delete(entity);
    }
}
