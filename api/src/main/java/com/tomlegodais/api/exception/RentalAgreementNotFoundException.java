package com.tomlegodais.api.exception;

import org.springframework.http.HttpStatus;

public class RentalAgreementNotFoundException extends ApiException {

    public RentalAgreementNotFoundException(Long id) {
        super(HttpStatus.NOT_FOUND, "Rental agreement with id: " + id + " not found");
    }
}
