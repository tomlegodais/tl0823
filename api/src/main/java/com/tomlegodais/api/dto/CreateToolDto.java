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
public class CreateToolDto {

    @NotBlank(message = "Code is required")
    private String code;

    @Valid
    @NotNull
    private CreateToolBrandDto brand;

    @Valid
    @NotNull
    private CreateToolTypeDto type;
}
