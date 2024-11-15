package daniel.proyectos.biblioteca.controller.v1;

import daniel.proyectos.biblioteca.dto.AuthRequest;
import daniel.proyectos.biblioteca.dto.DisableAccountRequest;
import daniel.proyectos.biblioteca.dto.UserDto;
import daniel.proyectos.biblioteca.entity.User;
import daniel.proyectos.biblioteca.service.UserService;
import daniel.proyectos.biblioteca.config.JwtUtil;
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
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Endpoint to register a new user
     */
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserDto userDto) {
        userService.register(userDto);

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userDto.getUsername(), userDto.getPassword())
        );

        String jwt = jwtUtil.generateToken(userDto.getUsername());

        return ResponseEntity.ok("Bearer " + jwt);
    }

    /**
     * Endpoint to log in
     */
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
     * Endpoint to reactivate account
     */
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




}
