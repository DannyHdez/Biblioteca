package daniel.proyectos.biblioteca.persistence;

import daniel.proyectos.biblioteca.entity.Editorial;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface IEditorialDAO {

    List<Editorial> findAll();
    Page<Editorial> findAll(Pageable page);
    Optional<Editorial> findById(Integer id);
    Editorial save(Editorial objeto);
    void deleteById(Integer id);
    void delete(Editorial entity);
}
