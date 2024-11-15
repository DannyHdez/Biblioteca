package daniel.proyectos.biblioteca.mapper.impl;

import daniel.proyectos.biblioteca.dto.UserDto;
import daniel.proyectos.biblioteca.entity.User;
import daniel.proyectos.biblioteca.mapper.UserMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserMapperImpl implements UserMapper {

    @Override
    public UserDto toDTO(User user) {
        if (user == null) return null;

        UserDto dto = new UserDto();
        dto.setName(user.getName());
        dto.setSurnames(user.getSurnames());
        dto.setEmail(user.getEmail());
        dto.setUsername(user.getUsername());
        dto.setUserType(user.getUserType());
        return dto;
    }

    @Override
    public User toEntity(UserDto userDto) {
        if (userDto == null) return null;

        User user = new User();
        user.setName(userDto.getName());
        user.setSurnames(userDto.getSurnames());
        user.setEmail(userDto.getEmail());
        user.setUsername(userDto.getUsername());
        user.setUserType(userDto.getUserType());
        return user;
    }

    @Override
    public List<UserDto> toDTOList(List<User> users) {
        return users.stream().map(this::toDTO).collect(Collectors.toList());
    }

    @Override
    public List<User> toEntityList(List<UserDto> userDtos) {
        return userDtos.stream().map(this::toEntity).collect(Collectors.toList());
    }
}
