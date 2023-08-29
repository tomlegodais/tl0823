package com.tomlegodais.api.exception;

import org.springframework.http.HttpStatus;

public class ToolTypeNotFoundException extends ApiException {
    public ToolTypeNotFoundException(Long id) {
        super(HttpStatus.NOT_FOUND, "Could not find tool type with id " + id);
    }
}
