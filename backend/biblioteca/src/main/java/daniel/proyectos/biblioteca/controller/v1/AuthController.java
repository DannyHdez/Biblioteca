package daniel.proyectos.biblioteca.controller.v1;

import daniel.proyectos.biblioteca.dto.AuthRequest;
import daniel.proyectos.biblioteca.dto.DisableAccountRequest;
import daniel.proyectos.biblioteca.dto.UserDto;
import daniel.proyectos.biblioteca.entity.User;
import daniel.proyectos.biblioteca.service.UserService;
import daniel.proyectos.biblioteca.config.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api/v1/auth")
@Tag(name = "Authentication", description = "Endpoints for user authentication")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) {
        User user = userService.findByUsername(authRequest.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        if (user.isAccountDisabled()) {
            return ResponseEntity.status(403).body("The account is disabled.");
        }

        try {
            // Authenticate the user only if the account is enabled
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
            );

            String jwt = jwtUtil.generateToken(authRequest.getUsername());
            return ResponseEntity.ok("Bearer " + jwt);

        } catch (AuthenticationException e) {
            return ResponseEntity.status(401).body("Invalid username or password");
        }
    }

    /**
     * Endpoint to log in
     */
    /*@Operation(
            summary = "Log in a user",
            description = "Authenticate a user and generate a JWT token.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(examples = {
                            @ExampleObject(name = "Valid credentials", value = "{ \"username\": \"john_doe\", \"password\": \"securePassword123\" }"),
                            @ExampleObject(name = "Invalid credentials", value = "{ \"username\": \"john_doe\", \"password\": \"wrongPassword\" }")
                    })
            )
    )
    @ApiResponse(responseCode = "200", description = "User authenticated successfully",
            content = @Content(mediaType = "application/json", examples = @ExampleObject(value = "{ \"token\": \"Bearer eyJhbGciOiJIUzUxMiJ9...\" }")))
    @ApiResponse(responseCode = "401", description = "Unauthorized - Invalid credentials",
            content = @Content(mediaType = "application/json", examples = @ExampleObject(value = "{ \"error\": \"Invalid username or password\" }")))*/

    /**
     * Endpoint to reactivate account
     */
    @Operation(
            summary = "Reactivate a disabled account",
            description = "Reactivate a user account that was previously disabled, within 30 days of deactivation.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(examples = {
                            @ExampleObject(name = "Valid request", value = "{ \"username\": \"john_doe\", \"password\": \"securePassword123\" }"),
                            @ExampleObject(name = "Expired request", value = "{ \"username\": \"john_doe\", \"password\": \"securePassword123\" }")
                    })
            )
    )
    @ApiResponse(responseCode = "200", description = "Account reactivated successfully",
            content = @Content(mediaType = "application/json", examples = @ExampleObject(value = "{ \"token\": \"Bearer eyJhbGciOiJIUzUxMiJ9...\" }")))
    @ApiResponse(responseCode = "403", description = "Forbidden - Account cannot be reactivated after 30 days",
            content = @Content(mediaType = "application/json", examples = @ExampleObject(value = "{ \"error\": \"The account cannot be reactivated after 30 days\" }")))
    @ApiResponse(responseCode = "401", description = "Unauthorized - Invalid username or password",
            content = @Content(mediaType = "application/json", examples = @ExampleObject(value = "{ \"error\": \"Invalid username or password\" }")))
    @PutMapping("/reactivate-account")
    public ResponseEntity<?> reactivateAccount(@RequestBody DisableAccountRequest request) {
        Optional<User> optionalUsuario = userService.findByUsername(request.getUsername());

        if (optionalUsuario.isEmpty()) {
            return ResponseEntity.status(401).body("Invalid username or password");
        }

        User user = optionalUsuario.get();

        if (!user.isAccountDisabled() || user.getDeactivationDate() == null) {
            return ResponseEntity.badRequest().body("The account is now active");
        }

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            return ResponseEntity.status(401).body("Invalid username or password");
        }

        long diffInMillis = Math.abs(new Date().getTime() - user.getDeactivationDate().getTime());
        long diffInDays = TimeUnit.DAYS.convert(diffInMillis, TimeUnit.MILLISECONDS);

        if (diffInDays > 30) {
            return ResponseEntity.status(403).body("The account cannot be reactivated after 30 days");
        }

        // Reactivate the account
        user.setAccountDisabled(false);
        user.setDeactivationDate(null);
        userService.save(user);

        // Generate JWT token for the reactivated user
        String jwt = jwtUtil.generateToken(user.getUsername());

        return ResponseEntity.ok("Bearer " + jwt);
    }

    /**
     * Endpoint to register a new user
     */
    @Operation(
            summary = "Register a new user",
            description = "Create a new account for a user.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(examples = {
                            @ExampleObject(name = "Valid request", value = """
                {
                    "username": "john_doe",
                    "password": "securePassword123",
                    "name": "John",
                    "surnames": "Doe",
                    "email": "john.doe@example.com",
                    "userType": "user"
                }
            """)
                    })
            )
    )
    @ApiResponse(responseCode = "201", description = "User registered successfully",
            content = @Content(mediaType = "application/json", examples = @ExampleObject(value = "{ \"message\": \"User registered successfully\" }")))
    @ApiResponse(responseCode = "400", description = "Bad request - Invalid input data",
            content = @Content(mediaType = "application/json", examples = @ExampleObject(value = "{ \"error\": \"Invalid input data\" }")))
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserDto userDto) {
        userService.register(userDto);

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userDto.getUsername(), userDto.getPassword())
        );

        String jwt = jwtUtil.generateToken(userDto.getUsername());

        return ResponseEntity.ok("Bearer " + jwt);
    }

}
