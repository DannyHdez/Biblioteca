package daniel.proyectos.biblioteca.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, unique = true)
    private String isbn;

    @Column(nullable = false, name = "title_es")
    private String titleEs;

    @Column(name = "title_en")
    private String titleEn;

    @Column(name = "title_ca")
    private String titleCa;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column
    private String language = "ES";

    @Column
    private Integer pageCount;

    @Column
    private String coverUrl;

    @Column(name = "publication_date")
    @Temporal(TemporalType.DATE)
    private Date publicationDate;

    @ManyToOne
    @JoinColumn(name = "editorial_id")
    private Editorial editorial;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnoreProperties("books")
    private User user;

    @JsonIgnoreProperties("books")
    @ManyToMany(mappedBy = "books")
    private List<Author> authors;

    @JsonIgnoreProperties("books")
    @ManyToMany
    @JoinTable(
            name = "book_genre",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id")
    )
    private List<Genre> genres;

}
