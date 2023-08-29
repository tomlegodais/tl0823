package com.tomlegodais.api.mapper;

import com.tomlegodais.api.dto.CreateToolBrandDto;
import com.tomlegodais.api.dto.ToolBrandDto;
import com.tomlegodais.api.model.ToolBrandModel;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ToolBrandMapper {

    ToolBrandDto modelToDto(ToolBrandModel model);

    ToolBrandModel createDtoToModel(CreateToolBrandDto dto);

    ToolBrandModel dtoToModel(ToolBrandDto brandDto);
}
