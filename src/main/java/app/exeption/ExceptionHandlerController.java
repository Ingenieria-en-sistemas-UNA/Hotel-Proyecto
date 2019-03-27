package app.exeption;

import java.io.IOException;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static org.springframework.http.HttpStatus.*;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class ExceptionHandlerController extends ResponseEntityExceptionHandler {
    @Bean
    public ErrorAttributes errorAttributes() {
        return new DefaultErrorAttributes() {
            @Override
            public Map<String, Object> getErrorAttributes(WebRequest webRequest,boolean includeStackTrace) {
                Map<String, Object> errorAttributes = super.getErrorAttributes(webRequest, true);
                return errorAttributes;
            }
        };
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String error = "Malformed JSON request";
        return buildResponseEntity(new Error(HttpStatus.BAD_REQUEST.value() , error, ex));
    }

    private ResponseEntity<Object> buildResponseEntity(Error error) {
        return ResponseEntity.status(error.getStatus()).body(error);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    protected ResponseEntity<Object> handleEntityNotFound(
            EntityNotFoundException ex) {
        Error apiError = new Error(NOT_FOUND.value());
        apiError.setMessage(ex.getMessage());
        return buildResponseEntity(apiError);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    protected ResponseEntity<Object> handleDataIntegrityViolationException(
            DataIntegrityViolationException ex) {
        Error apiError = new Error(CONFLICT.value());
        apiError.setMessage("Duplicate Id");
        return buildResponseEntity(apiError);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Object> handleAccessDeniedException(HttpServletResponse res) throws IOException {
        Error apiError = new Error(UNAUTHORIZED.value());
        apiError.setMessage("Acceso denegado");
        return buildResponseEntity(apiError);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleException() {
        Error apiError = new Error(BAD_REQUEST.value());
        apiError.setMessage("Algo ah ocurrido");
        return buildResponseEntity(apiError);
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<Object>  handleCustomException(CustomException ex) {
        Error apiError = new Error(UNAUTHORIZED.value());
        apiError.setMessage(ex.getMessage());
        return buildResponseEntity(apiError);
    }
}
