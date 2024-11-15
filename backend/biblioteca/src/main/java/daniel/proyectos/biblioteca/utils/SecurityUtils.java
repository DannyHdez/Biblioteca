package daniel.proyectos.biblioteca.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtils {

    /**
     * Retrieves the username of the authenticated user from the security context.
     *
     * @return the username of the authenticated user
     */
    public static String getAuthenticatedUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            return authentication.getName();
        }
        return null;
    }
}
