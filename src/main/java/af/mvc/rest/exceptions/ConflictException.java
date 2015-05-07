package af.mvc.rest.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by Adrian on 06/05/2015.
 */
@ResponseStatus(value = HttpStatus.CONFLICT)
public class ConflictException extends Exception {
    public ConflictException() {
        super();
    }

    public ConflictException(Throwable cause){
        super(cause);
    }


}
