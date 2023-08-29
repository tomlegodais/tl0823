package com.tomlegodais.api.service;

import com.tomlegodais.api.dto.CreateRentalAgreementDto;
import com.tomlegodais.api.dto.RentalAgreementDto;
import com.tomlegodais.api.exception.RentalAgreementNotFoundException;
import com.tomlegodais.api.mapper.RentalAgreementMapper;
import com.tomlegodais.api.repository.RentalAgreementRepository;
import com.tomlegodais.shared.service.CheckoutService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RentalAgreementService {

    private final RentalAgreementRepository repository;
    private final ToolService toolService;
    private final CheckoutService checkoutService;
    private final RentalAgreementMapper mapper;

    public RentalAgreementService(RentalAgreementRepository repository, ToolService toolService, CheckoutService checkoutService, RentalAgreementMapper mapper) {
        this.repository = repository;
        this.toolService = toolService;
        this.checkoutService = checkoutService;
        this.mapper = mapper;
    }

    public List<RentalAgreementDto> findAll() {
        return repository.findAll()
                .stream()
                .map(mapper::modelToDto)
                .collect(Collectors.toList());
    }

    public RentalAgreementDto findById(Long id) {
        return repository.findById(id)
                .map(mapper::modelToDto)
                .orElseThrow(() -> new RentalAgreementNotFoundException(id));
    }

    public RentalAgreementDto create(CreateRentalAgreementDto dto) {
        var toolModel = toolService.dtoToModel(toolService.findByCode(dto.getToolCode()));
        var tool = toolService.modelToDomain(toolModel);
        var domainRentalAgreement = checkoutService.checkout(tool, dto.getCheckoutDate(), dto.getRentalDays(), dto.getDiscountPercentage());
        var rentalAgreement = mapper.domainToModel(domainRentalAgreement);
        rentalAgreement.setTool(toolModel);

        var savedModel = repository.save(rentalAgreement);
        return mapper.modelToDto(savedModel);
    }
}
