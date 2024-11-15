package daniel.proyectos.biblioteca.service.impl;

import daniel.proyectos.biblioteca.entity.Editorial;
import daniel.proyectos.biblioteca.persistence.IEditorialDAO;
import daniel.proyectos.biblioteca.service.EditorialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EditorialServiceImpl implements EditorialService {

    @Autowired
    private IEditorialDAO dao;

    @Override
    public List<Editorial> findAll() {
        return dao.findAll();
    }

    @Override
    public Page<Editorial> findAll(Pageable page) {
        return dao.findAll(page);
    }

    @Override
    public Optional<Editorial> findById(Integer id) {
        return dao.findById(id);
    }

    @Override
    public Editorial save(Editorial objeto) {
        return dao.save(objeto);
    }

    @Override
    public void deleteById(Integer id) {
        dao.deleteById(id);
    }

    @Override
    public void delete(Editorial entity) {
        dao.delete(entity);
    }
}
