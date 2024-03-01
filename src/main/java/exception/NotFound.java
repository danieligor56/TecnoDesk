package exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class NotFound extends RuntimeException{

private static final long serialVersionUID = 1L;

public NotFound () {
	super();
}

public NotFound(String message) {
	
	super(message);
}

public NotFound(Throwable cause) {

	super(cause);
}

public NotFound(String message,Throwable cause) {
	
	super(message,cause);
}

}
