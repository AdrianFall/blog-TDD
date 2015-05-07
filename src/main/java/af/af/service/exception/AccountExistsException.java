package af.af.service.exception;

/**
 * Created by Adrian on 06/05/2015.
 */
public class AccountExistsException extends Exception {
    public AccountExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public AccountExistsException(Throwable cause) {
        super(cause);
    }

    public AccountExistsException() {
        super();
    }
}
