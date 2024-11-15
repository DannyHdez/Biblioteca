package daniel.proyectos.biblioteca.dto;

import lombok.Data;

@Data
public class UserDto {
    private String name;
    private String surnames;
    private String email;
    private String username;
    private String password;
    private String userType;
}
