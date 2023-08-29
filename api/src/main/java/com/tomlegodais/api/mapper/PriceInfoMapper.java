package com.tomlegodais.api.mapper;

import com.tomlegodais.api.dto.CreatePriceInfoDto;
import com.tomlegodais.api.dto.PriceInfoDto;
import com.tomlegodais.api.model.PriceInfoModel;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PriceInfoMapper {

    PriceInfoDto modelToDto(PriceInfoModel model);

    PriceInfoModel createDtoToModel(CreatePriceInfoDto dto);
}
