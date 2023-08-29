package com.tomlegodais.api.mapper;

import com.tomlegodais.api.dto.RentalAgreementDto;
import com.tomlegodais.api.model.RentalAgreementModel;
import com.tomlegodais.shared.model.RentalAgreement;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RentalAgreementMapper {

    @Mapping(source = "tool.code", target = "toolCode")
    @Mapping(source = "tool.type.name", target = "toolType")
    @Mapping(source = "tool.brand.name", target = "toolBrand")
    @Mapping(source = "discountPercentage", target = "discountPercent")
    RentalAgreementDto modelToDto(RentalAgreementModel model);

    @Mapping(source = "discountPercent", target = "discountPercentage")
    RentalAgreementModel domainToModel(RentalAgreement domain);
}
