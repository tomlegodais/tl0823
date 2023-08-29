package com.tomlegodais.api.controller;

import com.tomlegodais.api.dto.CreateToolDto;
import com.tomlegodais.api.dto.ToolDto;
import com.tomlegodais.api.service.ToolService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/tools")
public class ToolController {

    private final ToolService service;

    public ToolController(ToolService service) {
        this.service = service;
    }

    @GetMapping(produces = "application/json")
    public ResponseEntity<List<ToolDto>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping(path = "/id/{id}", produces = "application/json")
    public ResponseEntity<ToolDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping(path = "/code/{code}", produces = "application/json")
    public ResponseEntity<ToolDto> findByCode(@PathVariable String code) {
        return ResponseEntity.ok(service.findByCode(code));
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<ToolDto> create(@Valid @RequestBody CreateToolDto dto) {
        var createdDto = service.create(dto);
        var location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdDto.getId())
                .toUriString();

        return ResponseEntity.created(URI.create(location)).body(createdDto);
    }
}
