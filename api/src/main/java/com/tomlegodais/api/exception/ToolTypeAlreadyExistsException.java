package com.tomlegodais.api.exception;

import org.springframework.http.HttpStatus;

public class ToolTypeAlreadyExistsException extends ApiException {

    public ToolTypeAlreadyExistsException(String name) {
        super(HttpStatus.CONFLICT, "Tool type with name '" + name + "' already exists");
    }
}
