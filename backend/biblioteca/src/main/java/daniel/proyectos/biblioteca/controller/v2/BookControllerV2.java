package daniel.proyectos.biblioteca.controller.v2;

import daniel.proyectos.biblioteca.dto.BookDTO;
import daniel.proyectos.biblioteca.entity.User;
import daniel.proyectos.biblioteca.service.BookService;
import daniel.proyectos.biblioteca.service.UserService;
import daniel.proyectos.biblioteca.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/v2/books")
public class BookControllerV2 {

    @Autowired
    private BookService bookService;

    @Autowired
    private UserService userService;

    /**
     * Endpoint to display all the user's books
     */
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

}
