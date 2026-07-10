package com.example.carrito.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
@Schema(description = "entidad que representa el registro de un item de carrito")
@Data
public class CartItemRequestDTO {
    @Schema(description = "identificador de producto", example = "5")
    @NotNull(message = "ProductId is required")
    private Long productId;

    @Schema(description = "Cantidad de stock de un item en carrito", example = "25")
    @NotNull(message = "Cantidad is required")
    @Min(value = 1, message = "Cantidad must be greater than 0")
    private Integer stock;
}