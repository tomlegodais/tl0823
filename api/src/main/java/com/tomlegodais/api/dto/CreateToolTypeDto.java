package com.tomlegodais.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateToolTypeDto {

    @NotBlank(message = "Name is required")
    private String name;

    @Valid
    @NotNull
    private CreatePriceInfoDto priceInfo;
}
