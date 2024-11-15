package daniel.proyectos.biblioteca.dto;


import lombok.Data;

@Data
public class DisableAccountRequest {
    private String username;
    private String password;
}
