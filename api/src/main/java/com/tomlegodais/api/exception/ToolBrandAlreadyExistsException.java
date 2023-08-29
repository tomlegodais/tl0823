package com.tomlegodais.api.exception;

import org.springframework.http.HttpStatus;

public class ToolBrandAlreadyExistsException extends ApiException {

    public ToolBrandAlreadyExistsException(String brandName) {
        super(HttpStatus.CONFLICT, "Tool brand with name '" + brandName + "' already exists");
    }
}
