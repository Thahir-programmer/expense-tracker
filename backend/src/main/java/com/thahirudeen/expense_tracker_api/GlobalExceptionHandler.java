package com.thahirudeen.expense_tracker_api;

import java.util.LinkedHashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import jakarta.validation.ConstraintViolationException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log =
            LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(ExpenseNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleNotFound(
            ExpenseNotFoundException exception) {

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("error", exception.getMessage()));
    }

    // Invalid request body fields (@Valid on a DTO): one message per field.
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidation(
            MethodArgumentNotValidException exception) {

        Map<String, String> fields = new LinkedHashMap<>();
        exception.getBindingResult().getFieldErrors().forEach(fieldError ->
                fields.putIfAbsent(
                        fieldError.getField(),
                        fieldError.getDefaultMessage()));

        return ResponseEntity.badRequest()
                .body(Map.of("error", "Validation failed", "fields", fields));
    }

    // Invalid query parameters (@Validated on the controller).
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String, Object>> handleConstraintViolation(
            ConstraintViolationException exception) {

        Map<String, String> fields = new LinkedHashMap<>();
        exception.getConstraintViolations().forEach(violation -> {
            String path = violation.getPropertyPath().toString();
            String name = path.substring(path.lastIndexOf('.') + 1);
            fields.putIfAbsent(name, violation.getMessage());
        });

        return ResponseEntity.badRequest()
                .body(Map.of("error", "Validation failed", "fields", fields));
    }

    // Malformed JSON, a bad enum value, or a bad date format.
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, Object>> handleUnreadableBody(
            HttpMessageNotReadableException exception) {

        return ResponseEntity.badRequest()
                .body(Map.of("error", "Invalid request body"));
    }

    // A path or query value that cannot be converted, e.g. /expenses/abc.
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Map<String, Object>> handleTypeMismatch(
            MethodArgumentTypeMismatchException exception) {

        String message = "Invalid value for '" + exception.getName() + "'";

        return ResponseEntity.badRequest()
                .body(Map.of("error", message));
    }

    // Safety net: log the real error, never leak details to the client.
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleUnexpected(
            Exception exception) {

        // Spring's own web errors (404 route, 405 method, missing param...)
        // carry their own status: keep it instead of turning it into a 500.
        if (exception instanceof ErrorResponse errorResponse) {
            HttpStatus status = HttpStatus.valueOf(
                    errorResponse.getStatusCode().value());
            return ResponseEntity.status(status)
                    .body(Map.of("error", status.getReasonPhrase()));
        }

        log.error("Unexpected error", exception);

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("error", "Internal server error"));
    }
}
