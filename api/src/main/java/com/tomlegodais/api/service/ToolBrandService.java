package com.tomlegodais.api.service;

import com.tomlegodais.api.dto.CreateToolBrandDto;
import com.tomlegodais.api.dto.ToolBrandDto;
import com.tomlegodais.api.exception.ToolBrandAlreadyExistsException;
import com.tomlegodais.api.exception.ToolBrandNotFoundException;
import com.tomlegodais.api.mapper.ToolBrandMapper;
import com.tomlegodais.api.model.ToolBrandModel;
import com.tomlegodais.api.repository.ToolBrandRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ToolBrandService {

    private final ToolBrandRepository repository;
    private final ToolBrandMapper mapper;

    public ToolBrandService(ToolBrandRepository repository, ToolBrandMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public List<ToolBrandDto> findAll() {
        return repository.findAll()
                .stream()
                .map(mapper::modelToDto)
                .collect(Collectors.toList());
    }

    public ToolBrandDto findById(Long id) {
        return repository.findById(id)
                .map(mapper::modelToDto)
                .orElseThrow(() -> new ToolBrandNotFoundException(id));
    }

    public Optional<ToolBrandDto> findByName(String name) {
        return repository.findByName(name)
                .map(mapper::modelToDto);
    }

    public ToolBrandDto create(CreateToolBrandDto dto) {
        if (repository.existsByName(dto.getName())) {
            throw new ToolBrandAlreadyExistsException(dto.getName());
        }

        var savedModel = repository.save(mapper.createDtoToModel(dto));
        return mapper.modelToDto(savedModel);
    }

    public ToolBrandModel dtoToModel(ToolBrandDto brandDto) {
        return mapper.dtoToModel(brandDto);
    }
}
