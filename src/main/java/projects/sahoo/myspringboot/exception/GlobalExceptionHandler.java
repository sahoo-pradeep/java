package projects.sahoo.myspringboot.exception;

import java.time.LocalDateTime;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import projects.sahoo.myspringboot.models.utils.ErrorMessage;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorMessage> globalException(Exception e) {
    log.error(e.getMessage());
    ErrorMessage message = new ErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR.value(),
        "Something went wrong!",
        LocalDateTime.now());
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
  }

  @ExceptionHandler(EmployeeException.class)
  public ResponseEntity<ErrorMessage> employeeException(EmployeeException e) {
    log.error(e.getMessage());
    ErrorMessage message = new ErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR.value(),
        e.getMessage(), LocalDateTime.now());
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorMessage> employeeException(MethodArgumentNotValidException e) {
    log.error(e.getMessage());
    String messageString = e.getBindingResult().getAllErrors().stream()
        .map(ObjectError::getDefaultMessage).collect(Collectors.toList()).toString();
    ErrorMessage message = new ErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR.value(), messageString,
        LocalDateTime.now());
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(message);
  }
}
