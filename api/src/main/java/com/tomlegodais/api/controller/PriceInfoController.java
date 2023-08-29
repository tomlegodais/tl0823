package com.tomlegodais.api.controller;

import com.tomlegodais.api.dto.CreatePriceInfoDto;
import com.tomlegodais.api.dto.PriceInfoDto;
import com.tomlegodais.api.service.PriceInfoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/tools/price-info")
public class PriceInfoController {

    private final PriceInfoService service;

    public PriceInfoController(PriceInfoService service) {
        this.service = service;
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<List<PriceInfoDto>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping(path = "/{id}", produces = "application/json")
    public ResponseEntity<PriceInfoDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<PriceInfoDto> create(@Valid @RequestBody CreatePriceInfoDto dto) {
        var createdDto = service.create(dto);
        var location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdDto.getId())
                .toUriString();

        return ResponseEntity.created(URI.create(location)).body(createdDto);
    }
}
