package com.example.orden.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
@Schema(description = "entidad que representa la respuesta de un carrito")
@Data
public class CartResponseDTO {
    @Schema(description = "identificador de carrito", example = "28")
    private Long id;
    @Schema(description = "identificador de cliente", example = "3")
    private Long clientId;
    @Schema(description = "estatus de carrito, valores: CHECKOUT, CANCELLED, ACTIVE", example = "CHECKOUT")
    private String status;
}
