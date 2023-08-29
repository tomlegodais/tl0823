package com.tomlegodais.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ToolTypeDto {

    private Long id;
    private String name;
    private PriceInfoDto priceInfo;
}
