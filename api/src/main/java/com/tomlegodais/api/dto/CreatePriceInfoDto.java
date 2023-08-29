package com.tomlegodais.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreatePriceInfoDto {

    @NotNull
    @PositiveOrZero
    private Double dailyCharge;

    @NotNull
    private Boolean weekdayCharge;

    @NotNull
    private Boolean weekendCharge;

    @NotNull
    private Boolean holidayCharge;
}
