package exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ResourceExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(BadRequest.class)
	public ResponseEntity<StandardError> badRequestException(BadRequest e, HttpServletRequest request) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(new StandardError(HttpStatus.BAD_REQUEST, e.getMessage(), request.getRequestURI()));
	}
	
	@ExceptionHandler(InternalServerError.class)
	public ResponseEntity<StandardError> internalServerErro(InternalServerError e, HttpServletRequest request) {
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(new StandardError(HttpStatus.BAD_REQUEST, e.getMessage(), request.getRequestURI()));
	}
	
	@ExceptionHandler(NotFound.class)
	public ResponseEntity<StandardError> notFound(NotFound e, HttpServletRequest request) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body(new StandardError(HttpStatus.BAD_REQUEST, e.getMessage(), request.getRequestURI()));
	}
	@ExceptionHandler(Exception.class)
	public ResponseEntity<Object> handlerException(Exception e,Object body,HttpStatus status,HttpHeaders headers,WebRequest request) {
		return ResponseEntity.status(status)
				.body(new StandardError(status,e.getMessage(), request.getContextPath()));
	}
	
	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<Object> runtime(RuntimeException e,Object body,HttpStatus status,HttpHeaders headers,WebRequest request) {
		return ResponseEntity.status(status)
				.body(new StandardError(status,e.getMessage(), request.getContextPath()));
	}









}
