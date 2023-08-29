package com.tomlegodais.api.mapper;

import com.tomlegodais.api.dto.ToolTypeDto;
import com.tomlegodais.api.model.ToolTypeModel;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ToolTypeMapper {

    ToolTypeDto modelToDto(ToolTypeModel model);

    ToolTypeModel dtoToModel(ToolTypeDto dto);
}
