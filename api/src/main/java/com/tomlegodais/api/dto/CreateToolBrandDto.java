package com.tomlegodais.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateToolBrandDto {

    @NotBlank(message = "Name is required")
    private String name;
}
