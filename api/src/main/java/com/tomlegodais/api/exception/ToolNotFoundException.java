package com.tomlegodais.api.exception;

import org.springframework.http.HttpStatus;

public class ToolNotFoundException extends ApiException {

    public ToolNotFoundException(Long id) {
        super(HttpStatus.NOT_FOUND, "Tool with id " + id + " not found");
    }

    public ToolNotFoundException(String code) {
        super(HttpStatus.NOT_FOUND, "Tool with code " + code + " not found");
    }
}
