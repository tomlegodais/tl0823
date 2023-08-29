package com.tomlegodais.api.controller;

import com.tomlegodais.api.dto.CreateToolBrandDto;
import com.tomlegodais.api.dto.ToolBrandDto;
import com.tomlegodais.api.service.ToolBrandService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/tools/brands")
public class ToolBrandController {

    private final ToolBrandService service;

    public ToolBrandController(ToolBrandService service) {
        this.service = service;
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<List<ToolBrandDto>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping(path = "/{id}", produces = "application/json")
    public ResponseEntity<ToolBrandDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<ToolBrandDto> create(@Valid @RequestBody CreateToolBrandDto dto) {
        var createdDto = service.create(dto);
        var location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdDto.getId())
                .toUriString();

        return ResponseEntity.created(URI.create(location)).body(createdDto);
    }
}
