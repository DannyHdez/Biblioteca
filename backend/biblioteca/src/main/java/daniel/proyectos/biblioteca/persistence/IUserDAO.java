package daniel.proyectos.biblioteca.persistence;

import daniel.proyectos.biblioteca.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface IUserDAO {

    List<User> findAll();
    Page<User> findAll(Pageable page);
    Optional<User> findById(Integer id);
    User save(User objeto);
    void deleteById(Integer id);
    void delete(User entity);
    Optional<User> findByUsername(String username);
}
