package daniel.proyectos.biblioteca.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class CreateBookRequest {
    private String isbn;
    private String titleEs;
    private String titleEn;
    private String titleCa;
    private Date publicationDate;
    private Integer editorialId;
    private List<Integer> authorIds;
    private List<Integer> genreIds;
}
