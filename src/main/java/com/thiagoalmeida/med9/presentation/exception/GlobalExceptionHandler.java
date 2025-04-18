package com.thiagoalmeida.med9.presentation.exception;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ApiError> handleEntityNotFound(EntityNotFoundException ex, WebRequest request) {
        ApiError error = ApiError.builder()
            .path(request.getDescription(false))
            .message("Recurso não encontrado")
            .statusCode(HttpStatus.NOT_FOUND.value())
            .timestamp(LocalDateTime.now())
            .build();
        
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiError> handleIllegalArgument(IllegalArgumentException ex, WebRequest request) {
        ApiError error = ApiError.builder()
            .path(request.getDescription(false))
            .message(ex.getMessage())
            .statusCode(HttpStatus.BAD_REQUEST.value())
            .timestamp(LocalDateTime.now())
            .build();
        
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidationErrors(MethodArgumentNotValidException ex, WebRequest request) {
        List<ApiError.ValidationError> validationErrors = ex.getBindingResult()
            .getFieldErrors()
            .stream()
            .map(error -> ApiError.ValidationError.builder()
                .field(error.getField())
                .message(error.getDefaultMessage())
                .build())
            .toList();

        ApiError error = ApiError.builder()
            .path(request.getDescription(false))
            .message("Erro de validação")
            .statusCode(HttpStatus.BAD_REQUEST.value())
            .timestamp(LocalDateTime.now())
            .errors(validationErrors)
            .build();
        
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiError> handleConstraintViolation(ConstraintViolationException ex, WebRequest request) {
        List<ApiError.ValidationError> validationErrors = ex.getConstraintViolations()
            .stream()
            .map(violation -> ApiError.ValidationError.builder()
                .field(violation.getPropertyPath().toString())
                .message(violation.getMessage())
                .build())
            .toList();

        ApiError error = ApiError.builder()
            .path(request.getDescription(false))
            .message("Erro de validação")
            .statusCode(HttpStatus.BAD_REQUEST.value())
            .timestamp(LocalDateTime.now())
            .errors(validationErrors)
            .build();
        
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiError> handleBadCredentials(BadCredentialsException ex, WebRequest request) {
        ApiError error = ApiError.builder()
            .path(request.getDescription(false))
            .message("Credenciais inválidas")
            .statusCode(HttpStatus.UNAUTHORIZED.value())
            .timestamp(LocalDateTime.now())
            .build();
        
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiError> handleAccessDenied(AccessDeniedException ex, WebRequest request) {
        ApiError error = ApiError.builder()
            .path(request.getDescription(false))
            .message("Acesso negado")
            .statusCode(HttpStatus.FORBIDDEN.value())
            .timestamp(LocalDateTime.now())
            .build();
        
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGeneral(Exception ex, WebRequest request) {
        ApiError error = ApiError.builder()
            .path(request.getDescription(false))
            .message("Erro interno do servidor")
            .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
            .timestamp(LocalDateTime.now())
            .build();
        
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}
