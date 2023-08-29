package com.tomlegodais.api.exception;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import com.tomlegodais.api.dto.ApiExceptionDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.util.HashMap;

@ControllerAdvice
public class GlobalExceptionHandler {

    private final HttpServletRequest request;

    public GlobalExceptionHandler(HttpServletRequest request) {
        this.request = request;
    }

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<?> handleToolBrandAlreadyExistsException(ApiException ex) {
        return buildMessageResponse(ex.getMessage(), ex.getStatus());
    }

    @ExceptionHandler(UnrecognizedPropertyException.class)
    public ResponseEntity<?> handleUnrecognizedPropertyException(UnrecognizedPropertyException ex) {
        var message = "Unrecognized property '" + ex.getPropertyName() + "' found in request body";
        return buildMessageResponse(message, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidFormatException.class)
    public ResponseEntity<?> handleInvalidFormatException(InvalidFormatException ex) {
        String message;
        if (ex.getPath().stream().anyMatch(ref -> "checkoutDate".equals(ref.getFieldName()))) {
            message = "Invalid checkout date format. Must be mm/dd/yy";
        } else {
            message = "Invalid value for '" + ex.getPath().get(0).getFieldName() + "'";
        }

        return buildMessageResponse(message, HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<?> buildMessageResponse(String message, HttpStatus status) {
        var dto = new ApiExceptionDto(status, Instant.now(), request.getServletPath(), message);
        return ResponseEntity.status(status).body(dto);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationException(MethodArgumentNotValidException ex) {
        var errors = ex.getBindingResult().getFieldErrors().stream()
                .collect(HashMap::new, (m, v) -> m.put(v.getField(), v.getDefaultMessage()), HashMap::putAll);

        return ResponseEntity.badRequest().body(errors);
    }
}
