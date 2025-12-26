package br.com.ednei.userjwt.config;

import br.com.ednei.userjwt.exceptions.CustomTokenExpiredException;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception e) {
        log.error(e.getMessage());
        return ResponseEntity.internalServerError().body("Erro interno de inesperado");
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> handleException(RuntimeException e) {
        log.error(e.getMessage());
        return ResponseEntity.internalServerError().body("Erro interno de execução");
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> handleException(IllegalArgumentException e) {
        log.error(e.getMessage());
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(DisabledException.class)
    public ResponseEntity<?> handleException(DisabledException e) {
        log.error(e.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<?> handleException(BadCredentialsException e) {
        log.error(e.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
    }

    @ExceptionHandler(ChangeSetPersister.NotFoundException.class)
    public ResponseEntity<?> handleException(ChangeSetPersister.NotFoundException e) {
        log.error(e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(JWTVerificationException.class)
    public ResponseEntity<?> handleException(JWTVerificationException e) {
        log.error(e.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Token inválido");
    }

    @ExceptionHandler(JWTCreationException.class)
    public ResponseEntity<?> handleException(JWTCreationException e) {
        log.error(e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao criar Token");
    }

    @ExceptionHandler(CustomTokenExpiredException.class)
    public ResponseEntity<?> handleException(CustomTokenExpiredException e) {
        log.error(e.getCause().getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("O Token expirou");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }
}
