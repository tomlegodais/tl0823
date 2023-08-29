package com.tomlegodais.api.service;

import com.tomlegodais.api.dto.CreatePriceInfoDto;
import com.tomlegodais.api.dto.PriceInfoDto;
import com.tomlegodais.api.exception.PriceInfoNotFoundException;
import com.tomlegodais.api.mapper.PriceInfoMapper;
import com.tomlegodais.api.repository.PriceInfoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PriceInfoService {

    private final PriceInfoRepository repository;
    private final PriceInfoMapper mapper;

    public PriceInfoService(PriceInfoRepository repository, PriceInfoMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public List<PriceInfoDto> findAll() {
        return repository.findAll()
                .stream()
                .map(mapper::modelToDto)
                .collect(Collectors.toList());
    }

    public PriceInfoDto findById(Long id) {
        return repository.findById(id)
                .map(mapper::modelToDto)
                .orElseThrow(() -> new PriceInfoNotFoundException(id));
    }

    public PriceInfoDto create(CreatePriceInfoDto dto) {
        var savedModel = repository.save(mapper.createDtoToModel(dto));
        return mapper.modelToDto(savedModel);
    }
}
