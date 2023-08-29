package com.tomlegodais.api.mapper;

import com.tomlegodais.api.dto.ToolDto;
import com.tomlegodais.api.model.ToolModel;
import com.tomlegodais.shared.model.Tool;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ToolMapper {

    ToolDto modelToDto(ToolModel model);

    ToolModel dtoToModel(ToolDto dto);

    Tool modelToDomain(ToolModel model);
}
