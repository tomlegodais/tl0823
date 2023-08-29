package com.tomlegodais.api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateRentalAgreementDto {

    @NotBlank(message = "Tool code is required")
    private String toolCode;

    @NotNull(message = "Rental days is required")
    @Positive(message = "Rental days must be greater than 0")
    private Integer rentalDays;

    @NotNull(message = "Discount percentage is required")
    @Min(value = 0, message = "Discount percentage must be greater than or equal to 0")
    @Max(value = 100, message = "Discount percentage must be less than or equal to 100")
    private Integer discountPercentage;

    @JsonFormat(pattern = "MM/dd/yy")
    @NotNull(message = "Checkout date is required")
    private LocalDate checkoutDate;
}
