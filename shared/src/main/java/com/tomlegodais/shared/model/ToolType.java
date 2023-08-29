package com.tomlegodais.shared.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ToolType {

    private String name;
    private PriceInfo priceInfo;
}
