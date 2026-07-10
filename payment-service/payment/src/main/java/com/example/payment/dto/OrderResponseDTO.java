package com.example.payment.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "respuesta de informacion de una orden")
public class OrderResponseDTO {
    @Schema(description = "identificador de orden", example = "30")
    private Long id;
    @Schema(description = "identificador de cliente", example = "28")
    private Long clientId;
    @Schema(description = "identificador de carrito", example = "4")
    private Long cartId;
    @Schema(description = "Precio total de la orden completa", example = "$90.000")
    private Double total;
    @Schema(description = "estatus de la orden, valores: PAID, READY, PENDING_PAYMENT", example = "READY")
    private String status;
}
