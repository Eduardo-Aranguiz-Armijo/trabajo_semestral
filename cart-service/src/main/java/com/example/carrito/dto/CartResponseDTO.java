package com.example.carrito.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
@Schema(description = "entidad que representa la respuesta de un carrito")
@Data
public class CartResponseDTO {
    @Schema(description = "identificador de carrito", example = "3")
    private Long id;
    @Schema(description = "identificador de cliente", example = "25")
    private Long clientId;
    private String status;
}
