package choice.university.ivan.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.ws.client.WebServiceClientException;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<String> badRequest(BadRequestException ex) {
        String response = ex.getMessage();
        return new ResponseEntity<String>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> notFound(NotFoundException ex) {
        String response = ex.getMessage();
        return new ResponseEntity<String>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<String> conflict(ConflictException ex) {
        String response = ex.getMessage();
        return new ResponseEntity<String>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(WebServiceClientException.class)
    public ResponseEntity<String> connectionError(WebServiceClientException ex) {
        String response = "Microservice unavailable";
        return new ResponseEntity<String>(response, HttpStatus.SERVICE_UNAVAILABLE);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> serverError(Exception ex) {
        String response = ex.getMessage();
        return new ResponseEntity<String>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
