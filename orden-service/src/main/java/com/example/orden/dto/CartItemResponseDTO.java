package com.example.orden.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
@Schema(description = "entidad que representa la respuesta de un item de carrito")
@Data
public class CartItemResponseDTO {
    @Schema(description = "identificador de item en orden", example = "9")
    private Long id;
    @Schema(description = "identificador de carrito", example = "7")
    private Long cartId;
    @Schema(description = "identificador de producto", example = "25")
    private Long productId;
    @Schema(description = "cantidad de stock de item", example = "156 unidades")
    private Integer stock;
}
