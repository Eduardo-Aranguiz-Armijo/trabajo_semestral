package com.example.inventory.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "entidad que representa el registro de un producto en inventario")
public class InventoryRequestDTO {
    @Schema(description = "identificador unico del producto", example = "1")
    @NotNull(message = "ProductId is required")
    private Long productId;

    @Schema(description = "cantidad de stock del producto", example = "175 unidades")
    @NotNull(message = "Stock is required")
    @Min(value = 1, message = "Stock must be greater than 0")
    private Integer stock;
}