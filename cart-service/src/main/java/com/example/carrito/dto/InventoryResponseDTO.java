package com.example.carrito.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
@Schema(description = "entidad que representa la respuesta de un producto en inventario")
@Data
public class InventoryResponseDTO {
    @Schema(description = "identificador de producto en inventario", example = "25")
    private Long id;
    @Schema(description = "identificador de producto", example = "25")
    private Long productId;
    @Schema(description = "Cantidad disponible en inventario", example = "25")
    private Integer stock;
}
