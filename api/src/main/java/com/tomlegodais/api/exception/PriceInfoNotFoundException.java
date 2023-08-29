package com.tomlegodais.api.exception;

import org.springframework.http.HttpStatus;

public class PriceInfoNotFoundException extends ApiException {

    public PriceInfoNotFoundException(Long id) {
        super(HttpStatus.NOT_FOUND, "Price info not found with id: " + id);
    }
}
