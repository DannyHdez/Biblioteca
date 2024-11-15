package daniel.proyectos.biblioteca.exception;

public class InvalidPasswordException extends RuntimeException {

    private static final String DEFAULT_MESSAGE = "The old password is incorrect";

    public InvalidPasswordException() {
        super(DEFAULT_MESSAGE);
    }
}
