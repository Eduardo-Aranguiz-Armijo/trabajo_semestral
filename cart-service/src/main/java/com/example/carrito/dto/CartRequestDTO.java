package com.example.carrito.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "entidad que representa el cambio de estatus de un carrito")
public class CartRequestDTO {

    @Schema(description = "estatus de carrito, valores: CHECKOUT, CANCELLED, ACTIVE", example = "CHECKOUT")
    private String status;
}
