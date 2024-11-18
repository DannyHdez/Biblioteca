package daniel.proyectos.biblioteca.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "editorials")
public class Editorial {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column
    private String country;

    @Column
    private Integer foundedYear;

    @Column
    private String logo;

    @OneToMany(mappedBy = "editorial")
    private List<Book> books;

}
