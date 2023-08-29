package com.tomlegodais.shared.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Tool {

    private String code;
    private ToolType type;
    private ToolBrand brand;
}
