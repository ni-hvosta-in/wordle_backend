package nihvostain.wordle_backend.auth;

public class AuthException extends RuntimeException {
    public AuthException(String message) {
        super(message);
    }
}
