package mate.academy.winetaster.exception;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {
    private static final String TIMESTAMP = "timestamp";
    private static final String STATUS = "status";
    private static final String ERRORS = "errors";
    private static final String FIELD_MESSAGE_SEPARATOR = ": ";

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request
    ) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put(TIMESTAMP, LocalDateTime.now());
        body.put(STATUS, HttpStatus.BAD_REQUEST);
        List<String> errors = ex.getBindingResult().getAllErrors()
                .stream()
                .map(this::getErrorMessage)
                .toList();
        body.put(ERRORS, errors);
        return new ResponseEntity<>(body, headers, status);
    }

    private String getErrorMessage(ObjectError e) {
        if (e instanceof FieldError error) {
            String field = error.getField();
            String message = e.getDefaultMessage();
            return field + FIELD_MESSAGE_SEPARATOR + message;
        }
        return e.getDefaultMessage();
    }

    @ExceptionHandler(RegistrationException.class)
    public ResponseEntity<Object> handleRegistrationException(RegistrationException ex) {
        return buildResponseEntity(ex.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Object> handleUserNotFoundException(UserNotFoundException ex) {
        return buildResponseEntity(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(WineNotFoundException.class)
    public ResponseEntity<Object> handleWineNotFoundException(WineNotFoundException ex) {
        return buildResponseEntity(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ShoppingCartNotFoundException.class)
    public ResponseEntity<Object> handleShoppingCartNotFoundException(
            ShoppingCartNotFoundException ex
    ) {
        return buildResponseEntity(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CartItemNotFoundException.class)
    public ResponseEntity<Object> handleCartItemNotFoundException(CartItemNotFoundException ex) {
        return buildResponseEntity(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<Object> handleOrderNotFoundException(OrderNotFoundException ex) {
        return buildResponseEntity(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(OrderItemsNotFoundException.class)
    public ResponseEntity<Object> handleOrderItemsNotFoundException(
            OrderItemsNotFoundException ex
    ) {
        return buildResponseEntity(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CategoryNameAlreadyExistsException.class)
    public ResponseEntity<Object> handleCategoryNotFoundException(
            CategoryNameAlreadyExistsException ex
    ) {
        return buildResponseEntity(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(WineRegionAlreadyExistsException.class)
    public ResponseEntity<Object> handleWineRegionAlreadyExistsException(
            WineRegionAlreadyExistsException ex
    ) {
        return buildResponseEntity(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGlobalException(Exception ex) {
        return buildResponseEntity(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<Object> buildResponseEntity(String message, HttpStatus status) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put(TIMESTAMP, LocalDateTime.now());
        body.put(STATUS, status);
        body.put(ERRORS, List.of(message));
        return new ResponseEntity<>(body, status);
    }
}
