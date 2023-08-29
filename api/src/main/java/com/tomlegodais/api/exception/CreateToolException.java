package com.tomlegodais.api.exception;

import org.springframework.http.HttpStatus;

public class CreateToolException extends ApiException {
    public CreateToolException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }
}
