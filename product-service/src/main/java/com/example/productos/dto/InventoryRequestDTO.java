package com.example.productos.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
@Schema(description = "entidad que representa la creacion de un producto en inventario")
@Data
public class InventoryRequestDTO {
    @Schema(description = "Identificador de producto en inventario", example = "1")
    private Long productId;
    @Schema(description = "cantidad de stock de producto", example = "156 unidades")
    private Integer stock;
}
