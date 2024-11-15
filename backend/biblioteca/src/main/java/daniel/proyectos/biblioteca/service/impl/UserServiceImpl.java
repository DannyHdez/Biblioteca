package daniel.proyectos.biblioteca.service.impl;

import daniel.proyectos.biblioteca.dto.ChangePasswordRequest;
import daniel.proyectos.biblioteca.dto.UserDto;
import daniel.proyectos.biblioteca.entity.User;
import daniel.proyectos.biblioteca.exception.InvalidPasswordException;
import daniel.proyectos.biblioteca.mapper.UserMapper;
import daniel.proyectos.biblioteca.persistence.IUserDAO;
import daniel.proyectos.biblioteca.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private IUserDAO dao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserMapper userMapper;

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        return dao.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
    }

    @Override
    public List<User> findAll() {
        return dao.findAll();
    }

    @Override
    public Page<User> findAll(Pageable page) {
        return dao.findAll(page);
    }

    @Override
    public Optional<User> findById(Integer id) {
        return dao.findById(id);
    }

    @Override
    public User save(User objeto) {
        return dao.save(objeto);
    }

    @Override
    public void deleteById(Integer id) {
        dao.deleteById(id);
    }

    @Override
    public void delete(User entity) {
        dao.delete(entity);
    }

    @Override
    public void register(UserDto userDto) {
        // Using the mapper to convert UserDto to User
        User user = userMapper.toEntity(userDto);
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setRegistrationDate(new Date());

        dao.save(user);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return dao.findByUsername(username);
    }


    public Optional<UserDto> findUserDtoById(Integer id) {
        return dao.findById(id).map(userMapper::toDTO);
    }

    public List<UserDto> findAllUserDtos() {
        return userMapper.toDTOList(dao.findAll());
    }

    @Override
    public void changePassword(User user, ChangePasswordRequest request) {
        if (!passwordEncoder.matches(request.getOldPassword(), user.getPassword())) {
            throw new InvalidPasswordException(); // No message is passed because we have it in exception
        }
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        save(user);
    }
}
