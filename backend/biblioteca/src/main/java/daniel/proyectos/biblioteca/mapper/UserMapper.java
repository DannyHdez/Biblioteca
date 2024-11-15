package daniel.proyectos.biblioteca.mapper;

import daniel.proyectos.biblioteca.dto.UserDto;
import daniel.proyectos.biblioteca.entity.User;

import java.util.List;

public interface UserMapper {
    UserDto toDTO(User user);
    User toEntity(UserDto userDto);
    List<UserDto> toDTOList(List<User> users);
    List<User> toEntityList(List<UserDto> userDtos);
}
