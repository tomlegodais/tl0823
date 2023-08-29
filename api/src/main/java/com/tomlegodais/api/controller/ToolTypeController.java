package com.tomlegodais.api.controller;

import com.tomlegodais.api.dto.CreateToolTypeDto;
import com.tomlegodais.api.dto.ToolTypeDto;
import com.tomlegodais.api.service.ToolTypeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/tools/types")
public class ToolTypeController {

    private final ToolTypeService service;

    public ToolTypeController(ToolTypeService service) {
        this.service = service;
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<List<ToolTypeDto>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping(path = "/{id}", produces = "application/json")
    public ResponseEntity<ToolTypeDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<ToolTypeDto> create(@Valid @RequestBody CreateToolTypeDto dto) {
        var createdDto = service.create(dto);
        var location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdDto.getId())
                .toUriString();

        return ResponseEntity.created(URI.create(location)).body(createdDto);
    }
}
