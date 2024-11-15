package daniel.proyectos.biblioteca.controller.v2;

import daniel.proyectos.biblioteca.config.JwtUtil;
import daniel.proyectos.biblioteca.dto.ChangePasswordRequest;
import daniel.proyectos.biblioteca.dto.DisableAccountRequest;
import daniel.proyectos.biblioteca.dto.UserDto;
import daniel.proyectos.biblioteca.dto.UserModifyDto;
import daniel.proyectos.biblioteca.entity.User;
import daniel.proyectos.biblioteca.service.UserService;
import daniel.proyectos.biblioteca.utils.SecurityUtils;
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
public class UserControllerV2 {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    /**
     * Endpoint to disable the authenticated user account
     */
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


    /**
     * Endpoint to obtain authenticated user information
     */
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
    @PutMapping("/me/change-password")
    public String changePassword(@RequestBody ChangePasswordRequest request) {
        String username = SecurityUtils.getAuthenticatedUsername();

        User user = userService.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        userService.changePassword(user, request);

        return "Password updated successfully";
    }

}
