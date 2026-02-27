package com.banking.spring.clients.ms_clients.exception;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ClientNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(ClientNotFoundException ex) {
        return buildResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(ClientAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleConflict(ClientAlreadyExistsException ex) {
        return buildResponse(HttpStatus.CONFLICT, ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(MethodArgumentNotValidException ex) {

        String message = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(e -> e.getField() + ": " + e.getDefaultMessage())
                .collect(Collectors.joining(", "));

        return buildResponse(HttpStatus.BAD_REQUEST, message);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneric(Exception ex) {
        log.error("Error inesperado: {}", ex.getMessage(), ex);
        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Ocurrió un error inesperado");
    }

    private ResponseEntity<ErrorResponse> buildResponse(HttpStatus status, String message) {
        log.warn("{} - {}", status.value(), message);
        return ResponseEntity.status(status)
                .body(new ErrorResponse(LocalDateTime.now(), status.value(), message));
    }

    public record ErrorResponse(LocalDateTime timestamp, int status, String message) {
    }
}