package com.example.carrito.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
@Schema(description = "entidad que representa la respuesta de un item de carrito")
@Data
public class CartItemResponseDTO {
    @Schema(description = "identificador de item producto", example = "8")
    private Long id;
    @Schema(description = "identificador de carrito", example = "5")
    private Long cartId;
    @Schema(description = "identificador de producto", example = "2")
    private Long productId;
    @Schema(description = "Cantidad disponible en inventario", example = "25")
    private Integer stock;
}
