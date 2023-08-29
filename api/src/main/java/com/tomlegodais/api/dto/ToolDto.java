package com.tomlegodais.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ToolDto {

    private Long id;
    private String code;
    private ToolTypeDto type;
    private ToolBrandDto brand;
}
