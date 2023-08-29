package com.tomlegodais.api.controller;

import com.tomlegodais.api.dto.CreateRentalAgreementDto;
import com.tomlegodais.api.dto.RentalAgreementDto;
import com.tomlegodais.api.service.RentalAgreementService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/tools/rental-agreements")
public class RentalAgreementController {

    private final RentalAgreementService service;

    public RentalAgreementController(RentalAgreementService service) {
        this.service = service;
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<List<RentalAgreementDto>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping(path = "/{id}", produces = "application/json")
    public ResponseEntity<RentalAgreementDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<RentalAgreementDto> create(@Valid @RequestBody CreateRentalAgreementDto dto) {
        var createdDto = service.create(dto);
        var location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdDto.getId())
                .toUriString();

        return ResponseEntity.created(URI.create(location)).body(createdDto);
    }
}
