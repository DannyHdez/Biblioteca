package daniel.proyectos.biblioteca.dto;

import lombok.Data;
import java.util.Date;
import java.util.List;

@Data
public class BookDTO {
    private int id;
    private String titleEs;
    private String titleEn;
    private String titleCa;
    private EditorialInfoDTO editorial;
    private Date publicationDate;
    private List<AuthorInfoDTO> authors;
    private List<GenreInfoDTO> genres;
}
