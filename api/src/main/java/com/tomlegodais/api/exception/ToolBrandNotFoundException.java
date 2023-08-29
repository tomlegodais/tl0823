package com.tomlegodais.api.exception;

import org.springframework.http.HttpStatus;

public class ToolBrandNotFoundException extends ApiException {

    public ToolBrandNotFoundException(Long id) {
        super(HttpStatus.NOT_FOUND, "Tool brand not found with id: " + id);
    }
}
