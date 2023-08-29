package com.tomlegodais.api.service;

import com.tomlegodais.api.dto.CreateToolTypeDto;
import com.tomlegodais.api.dto.ToolTypeDto;
import com.tomlegodais.api.exception.ToolTypeAlreadyExistsException;
import com.tomlegodais.api.exception.ToolTypeNotFoundException;
import com.tomlegodais.api.mapper.ToolTypeMapper;
import com.tomlegodais.api.model.PriceInfoModel;
import com.tomlegodais.api.model.ToolTypeModel;
import com.tomlegodais.api.repository.ToolTypeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ToolTypeService {

    private final ToolTypeRepository repository;
    private final ToolTypeMapper mapper;
    private final PriceInfoService priceInfoService;

    public ToolTypeService(ToolTypeRepository repository, ToolTypeMapper mapper, PriceInfoService priceInfoService) {
        this.repository = repository;
        this.mapper = mapper;
        this.priceInfoService = priceInfoService;
    }

    public List<ToolTypeDto> findAll() {
        return repository.findAll()
                .stream()
                .map(mapper::modelToDto)
                .collect(Collectors.toList());
    }

    public ToolTypeDto findById(Long id) {
        return repository.findById(id)
                .map(mapper::modelToDto)
                .orElseThrow(() -> new ToolTypeNotFoundException(id));
    }

    public Optional<ToolTypeDto> findByName(String name) {
        return repository.findByName(name)
                .map(mapper::modelToDto);
    }

    public ToolTypeDto create(CreateToolTypeDto dto) {
        if (repository.existsByName(dto.getName())) {
            throw new ToolTypeAlreadyExistsException(dto.getName());
        }

        var priceInfoDto = priceInfoService.create(dto.getPriceInfo());
        var model = new ToolTypeModel();

        model.setName(dto.getName());
        model.setPriceInfo(new PriceInfoModel(priceInfoDto.getId(),
                priceInfoDto.getDailyCharge(),
                priceInfoDto.isWeekdayCharge(),
                priceInfoDto.isWeekendCharge(),
                priceInfoDto.isHolidayCharge()));


        var savedModel = repository.save(model);
        return mapper.modelToDto(savedModel);
    }

    public ToolTypeModel dtoToModel(ToolTypeDto dto) {
        return mapper.dtoToModel(dto);
    }
}
