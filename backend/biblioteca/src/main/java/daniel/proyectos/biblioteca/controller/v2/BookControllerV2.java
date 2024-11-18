package daniel.proyectos.biblioteca.controller.v2;

import daniel.proyectos.biblioteca.dto.BookDTO;
import daniel.proyectos.biblioteca.dto.CreateBookRequest;
import daniel.proyectos.biblioteca.entity.User;
import daniel.proyectos.biblioteca.service.BookService;
import daniel.proyectos.biblioteca.service.UserService;
import daniel.proyectos.biblioteca.utils.SecurityUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/v2/books")
@Tag(name = "Books", description = "Endpoints for managing books")
public class BookControllerV2 {

    @Autowired
    private BookService bookService;

    @Autowired
    private UserService userService;

    /**
     * Endpoint to display all the user's books
     */
    @Operation(
            summary = "Get all books for the authenticated user",
            description = "Retrieve all books associated with the currently authenticated user.",
            security = @SecurityRequirement(name = "BearerAuth")
    )
    @ApiResponse(responseCode = "200", description = "Books retrieved successfully",
            content = @Content(mediaType = "application/json", examples = @ExampleObject(value = """
        [
            {
                "id": 1,
                "titleEs": "Don Quijote",
                "titleEn": "Don Quixote",
                "titleCa": "Don Quixot",
                "editorial": { "id": 1, "name": "Editorial Anaya" },
                "editionDate": "1605-01-01",
                "authors": [ { "id": 1, "name": "Miguel", "surnames": "de Cervantes" } ],
                "genres": [ { "id": 1, "name": "Adventure" } ]
            },
            {
                "id": 2,
                "titleEs": "Cien Años de Soledad",
                "titleEn": "One Hundred Years of Solitude",
                "titleCa": null,
                "editorial": { "id": 2, "name": "Editorial Alfaguara" },
                "editionDate": "1967-05-30",
                "authors": [ { "id": 2, "name": "Gabriel", "surnames": "García Márquez" } ],
                "genres": [ { "id": 2, "name": "Magical Realism" } ]
            }
        ]
    """)))
    @ApiResponse(responseCode = "401", description = "Unauthorized - User is not authenticated",
            content = @Content(mediaType = "application/json", examples = @ExampleObject(value = "{ \"error\": \"Unauthorized - User is not authenticated\" }")))
    @GetMapping
    public List<BookDTO> getAll() {
        // Get the current authentication
        String username = SecurityUtils.getAuthenticatedUsername();

        User user = userService.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return bookService.findAllByUserId(user.getId());
    }

    /**
     * Endpoint to display the book by book id and user id
     */
    @Operation(
            summary = "Get a book by ID",
            description = "Retrieve details of a specific book associated with the authenticated user.",
            security = @SecurityRequirement(name = "BearerAuth")
    )
    @ApiResponse(responseCode = "200", description = "Book retrieved successfully",
            content = @Content(mediaType = "application/json", examples = @ExampleObject(value = """
        {
            "id": 1,
            "titleEs": "Don Quijote",
            "titleEn": "Don Quixote",
            "titleCa": "Don Quixot",
            "editorial": { "id": 1, "name": "Editorial Anaya" },
            "editionDate": "1605-01-01",
            "authors": [ { "id": 1, "name": "Miguel", "surnames": "de Cervantes" } ],
            "genres": [ { "id": 1, "name": "Adventure" } ]
        }
    """)))
    @ApiResponse(responseCode = "404", description = "Not found - Book does not exist or does not belong to the user",
            content = @Content(mediaType = "application/json", examples = @ExampleObject(value = "{ \"error\": \"Book not found or unauthorized\" }")))
    @GetMapping("/{id}")
    public ResponseEntity<BookDTO> getById(@PathVariable Integer id) {
        String username = SecurityUtils.getAuthenticatedUsername();

        int usuarioId = userService.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"))
                .getId();

        return bookService.findById(id, usuarioId)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new RuntimeException("Book not found or unauthorized"));
    }

    /**
     * Endpoint to create a new book for the authenticated user.
     */
    @Operation(
            summary = "Create a new book",
            description = "Create a new book associated with the authenticated user. Requires a valid JWT token.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(mediaType = "application/json", examples = {
                            @ExampleObject(
                                    name = "Valid book request",
                                    value = """
                {
                    "isbn": "978-3-16-148410-0",
                    "titleEs": "El Principito",
                    "titleEn": "The Little Prince",
                    "titleCa": "El Petit Príncep",
                    "publicationDate": "2024-01-01",
                    "editorial": { 
                        "id": 1, 
                        "name": "Editorial XYZ" 
                    },
                    "authors": [
                        { "id": 1, "name": "Antoine", "surnames": "de Saint-Exupéry" }
                    ],
                    "genres": [
                        { "id": 1, "name": "Fiction" }
                    ]
                }
                """
                            ),
                            @ExampleObject(
                                    name = "Invalid book request",
                                    value = """
                {
                    "isbn": "",
                    "titleEs": "",
                    "publicationDate": "not-a-date",
                    "editorial": null,
                    "authors": [],
                    "genres": []
                }
                """
                            )
                    })
            )
    )
    @ApiResponse(responseCode = "201", description = "Book created successfully", content = @Content(mediaType = "application/json", examples = {
            @ExampleObject(
                    name = "Book created response",
                    value = """
        {
            "id": 123,
            "isbn": "978-3-16-148410-0",
            "titleEs": "El Principito",
            "titleEn": "The Little Prince",
            "titleCa": "El Petit Príncep",
            "publicationDate": "2024-01-01",
            "editorial": { 
                "id": 1, 
                "name": "Editorial XYZ" 
            },
            "authors": [
                { "id": 1, "name": "Antoine", "surnames": "de Saint-Exupéry" }
            ],
            "genres": [
                { "id": 1, "name": "Fiction" }
            ]
        }
        """
            )
    }))
    @ApiResponse(responseCode = "400", description = "Bad request - Invalid input data")
    @ApiResponse(responseCode = "401", description = "Unauthorized - Token missing or invalid")
    @PostMapping("/createBook")
    public ResponseEntity<BookDTO> createBook(
            @RequestBody CreateBookRequest request,
            @RequestHeader("Authorization") String token) {

        // Extract the username from the token
        String username = SecurityUtils.getAuthenticatedUsername();

        // Find the authenticated user
        User user = userService.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Delegate the creation process to the service
        BookDTO createdBook = bookService.createBook(request, user);

        return ResponseEntity.status(201).body(createdBook);
    }

}
