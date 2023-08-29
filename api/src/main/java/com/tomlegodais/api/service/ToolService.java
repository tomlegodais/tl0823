package com.tomlegodais.api.service;

import com.tomlegodais.api.dto.CreateToolDto;
import com.tomlegodais.api.dto.ToolDto;
import com.tomlegodais.api.exception.CreateToolException;
import com.tomlegodais.api.exception.ToolNotFoundException;
import com.tomlegodais.api.mapper.ToolMapper;
import com.tomlegodais.api.model.ToolModel;
import com.tomlegodais.api.repository.ToolRepository;
import com.tomlegodais.shared.model.Tool;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ToolService {

    private final ToolRepository repository;
    private final ToolBrandService brandService;
    private final ToolTypeService typeService;
    private final ToolMapper mapper;

    public ToolService(ToolRepository repository, ToolBrandService brandService, ToolTypeService typeService, ToolMapper mapper) {
        this.repository = repository;
        this.brandService = brandService;
        this.typeService = typeService;
        this.mapper = mapper;
    }

    public List<ToolDto> findAll() {
        return repository.findAll()
                .stream()
                .map(mapper::modelToDto)
                .collect(Collectors.toList());
    }

    public ToolDto findById(Long id) {
        return repository.findById(id)
                .map(mapper::modelToDto)
                .orElseThrow(() -> new ToolNotFoundException(id));
    }

    public ToolDto findByCode(String code) {
        return repository.findByCode(code)
                .map(mapper::modelToDto)
                .orElseThrow(() -> new ToolNotFoundException(code));
    }

    public ToolDto create(CreateToolDto dto) {
        if (repository.existsByCode(dto.getCode())) {
            throw new CreateToolException("Tool with code: " + dto.getCode() + " already exists");
        }

        var brandDto = brandService.findByName(dto.getBrand().getName())
                .orElseGet(() -> brandService.create(dto.getBrand()));

        var brandModel = brandService.dtoToModel(brandDto);
        if (brandModel == null) {
            throw new CreateToolException("Error occurred while creating tool with brand: " + dto.getBrand().getName());
        }

        var typeDto = typeService.findByName(dto.getType().getName())
                .orElseGet(() -> typeService.create(dto.getType()));

        var typeModel = typeService.dtoToModel(typeDto);
        if (typeModel == null) {
            throw new CreateToolException("Error occurred while creating tool with type: " + dto.getType().getName());
        }

        var toolModel = new ToolModel();
        toolModel.setCode(dto.getCode());
        toolModel.setBrand(brandModel);
        toolModel.setType(typeModel);

        var savedModel = repository.save(toolModel);
        return mapper.modelToDto(savedModel);
    }

    public ToolModel dtoToModel(ToolDto dto) {
        return mapper.dtoToModel(dto);
    }

    public Tool modelToDomain(ToolModel model) {
        return mapper.modelToDomain(model);
    }
}
