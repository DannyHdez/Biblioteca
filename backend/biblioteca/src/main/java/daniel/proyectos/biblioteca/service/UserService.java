package daniel.proyectos.biblioteca.service;

import daniel.proyectos.biblioteca.dto.ChangePasswordRequest;
import daniel.proyectos.biblioteca.dto.UserDto;
import daniel.proyectos.biblioteca.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Optional;

public interface UserService extends UserDetailsService {

    List<User> findAll();
    Page<User> findAll(Pageable page);
    Optional<User> findById(Integer id);
    User save(User objeto);
    void deleteById(Integer id);
    void delete(User entity);

    void register(UserDto userDto);
    Optional<User> findByUsername(String username);
    void changePassword (User user, ChangePasswordRequest request);

    // Métodos adicionales
    Optional<UserDto> findUserDtoById(Integer id);
    List<UserDto> findAllUserDtos();
}

