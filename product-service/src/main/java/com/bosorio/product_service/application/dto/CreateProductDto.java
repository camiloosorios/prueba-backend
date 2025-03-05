package com.bosorio.product_service.application.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CreateProductDto {

    @NotEmpty(message = "Product name is required")
    private String name;

    @NotEmpty(message = "Product description is required")
    private String description;

    @NotNull(message = "Product price is required")
    @Min(value = 1, message = "Product price must be greater than zero")
    private BigDecimal price;

    @NotNull(message = "Product stock is required")
    @Min(value = 0, message = "Product stock cannot be less than zero")
    private Integer stock;

}
