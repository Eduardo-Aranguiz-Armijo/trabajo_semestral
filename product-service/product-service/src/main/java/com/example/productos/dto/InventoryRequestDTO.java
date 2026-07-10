package com.example.productos.dto;

import lombok.Data;

import io.swagger.v3.oas.annotations.media.Schema;

@Data
@Schema(description = "Datos para el registro inicial de inventario del producto")
public class InventoryRequestDTO {
    @Schema(description = "ID del producto", example = "1")
    private Long productId;

    @Schema(description = "Stock inicial", example = "50")
    private Integer stock;
}
