package daniel.proyectos.biblioteca.controller.v2;

import daniel.proyectos.biblioteca.config.JwtUtil;
import daniel.proyectos.biblioteca.dto.ChangePasswordRequest;
import daniel.proyectos.biblioteca.dto.DisableAccountRequest;
import daniel.proyectos.biblioteca.dto.UserDto;
import daniel.proyectos.biblioteca.dto.UserModifyDto;
import daniel.proyectos.biblioteca.entity.User;
import daniel.proyectos.biblioteca.service.UserService;
import daniel.proyectos.biblioteca.utils.SecurityUtils;
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
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/api/v2/users")
@Tag(name = "Users", description = "Endpoints for user management")
public class UserControllerV2 {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    /**
     * Endpoint to obtain authenticated user information
     */
    @Operation(
            summary = "Get the authenticated user's information",
            description = "Retrieve the profile of the currently authenticated user."
    )
    @ApiResponse(responseCode = "200", description = "User profile retrieved successfully",
            content = @Content(mediaType = "application/json", examples = @ExampleObject(value = """
        {
            "id": 1,
            "username": "john_doe",
            "name": "John",
            "surnames": "Doe",
            "email": "john.doe@example.com",
            "userType": "admin"
        }
    """)))
    @ApiResponse(responseCode = "401", description = "Unauthorized - User is not authenticated",
            content = @Content(mediaType = "application/json", examples = @ExampleObject(value = "{ \"error\": \"Unauthorized - User is not authenticated\" }")))
    @GetMapping("/me")
    public UserDto getAuthenticatedUser() {
        String username = SecurityUtils.getAuthenticatedUsername();

        return userService.findByUsername(username)
                .map(user -> userService.findUserDtoById(user.getId()).orElse(null))
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    /**
     * Endpoint to update authenticated user information
     */
    @Operation(
            summary = "Update the authenticated user's information",
            description = "Modify the profile details of the currently authenticated user.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(examples = {
                            @ExampleObject(name = "Valid update", value = """
                {
                    "name": "John",
                    "surnames": "Doe",
                    "email": "john.doe@example.com"
                }
            """),
                            @ExampleObject(name = "Invalid email format", value = """
                {
                    "name": "John",
                    "surnames": "Doe",
                    "email": "notAnEmail"
                }
            """)
                    })
            )
    )
    @ApiResponse(responseCode = "200", description = "User profile updated successfully",
            content = @Content(mediaType = "application/json", examples = @ExampleObject(value = """
        {
            "name": "John",
            "surnames": "Doe",
            "email": "john.doe@example.com"
        }
    """)))
    @ApiResponse(responseCode = "400", description = "Bad request - Invalid input data",
            content = @Content(mediaType = "application/json", examples = @ExampleObject(value = "{ \"error\": \"Invalid input data\" }")))
    @ApiResponse(responseCode = "401", description = "Unauthorized - User is not authenticated",
            content = @Content(mediaType = "application/json", examples = @ExampleObject(value = "{ \"error\": \"User not authenticated\" }")))
    @PutMapping("/me")
    public UserModifyDto updateAuthenticatedUser(@RequestBody UserModifyDto usermodifyDto) {
        String username = SecurityUtils.getAuthenticatedUsername();

        return userService.findByUsername(username)
                .map(user -> {
                    user.setName(usermodifyDto.getName());
                    user.setSurnames(usermodifyDto.getSurnames());
                    user.setEmail(usermodifyDto.getEmail());

                    // Saves the updated user to the database and assigns the result to `updatedUsuario`
                    User updatedUser = userService.save(user);

                    return usermodifyDto;
                })
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    /**
     * Endpoint to change the password of the authenticated user
     */
    @Operation(
            summary = "Change the password for the authenticated user",
            description = "Update the password for the currently authenticated user.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(examples = {
                            @ExampleObject(name = "Valid password change", value = """
                {
                    "oldPassword": "currentPassword123",
                    "newPassword": "newSecurePassword456"
                }
            """),
                            @ExampleObject(name = "Invalid old password", value = """
                {
                    "oldPassword": "wrongPassword",
                    "newPassword": "newSecurePassword456"
                }
            """)
                    })
            )
    )
    @ApiResponse(responseCode = "200", description = "Password updated successfully",
            content = @Content(mediaType = "application/json", examples = @ExampleObject(value = "{ \"message\": \"Password updated successfully\" }")))
    @ApiResponse(responseCode = "400", description = "Bad request - Invalid input data",
            content = @Content(mediaType = "application/json", examples = @ExampleObject(value = "{ \"error\": \"Password does not meet requirements\" }")))
    @ApiResponse(responseCode = "401", description = "Unauthorized - User is not authenticated",
            content = @Content(mediaType = "application/json", examples = @ExampleObject(value = "{ \"error\": \"User not authenticated\" }")))
    @PutMapping("/me/change-password")
    public String changePassword(@RequestBody ChangePasswordRequest request) {
        String username = SecurityUtils.getAuthenticatedUsername();

        User user = userService.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        userService.changePassword(user, request);

        return "Password updated successfully";
    }

    /**
     * Endpoint to disable the authenticated user account
     */
    @Operation(
            summary = "Disable the authenticated user's account",
            description = "Deactivate the currently authenticated user's account.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(examples = {
                            @ExampleObject(name = "Valid request", value = """
                {
                    "username": "john_doe",
                    "password": "securePassword123"
                }
            """)
                    })
            )
    )
    @ApiResponse(responseCode = "200", description = "Account disabled successfully",
            content = @Content(mediaType = "application/json", examples = @ExampleObject(value = "{ \"message\": \"Account disabled successfully\" }")))
    @ApiResponse(responseCode = "401", description = "Unauthorized - Invalid username or password",
            content = @Content(mediaType = "application/json", examples = @ExampleObject(value = "{ \"error\": \"Invalid username or password\" }")))
    @PutMapping("/disable-account")
    public ResponseEntity<?> disableAccount(@RequestBody DisableAccountRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );

            User user = (User) authentication.getPrincipal();

            // Disable the account and set the deactivation date
            user.setAccountDisabled(true);
            user.setDeactivationDate(new Date());
            userService.save(user);

            return ResponseEntity.ok("Account disabled successfully");

        } catch (AuthenticationException e) {
            return ResponseEntity.status(401).body("Incorrect username or password");
        }
    }

}
