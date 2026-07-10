package com.example.orden.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
@Schema(description = "entidad que representa la informacion de una orden")
@Data
public class OrderResponseDTO {
    @Schema(description = "identificador de orden", example = "1")
    private Long id;
    @Schema(description = "identificador de cliente", example = "3")
    private Long clientId;
    @Schema(description = "identificador de carrito", example = "1")
    private Long cartId;
    @Schema(description = "Precio total de la orden completa", example = "$90.000")
    private Double total;
    @Schema(description = "estatus de la orden, valores: PAID, READY, PENDING_PAYMENT", example = "READY")
    private String status;
    @Schema(description = "fecha de creacion", example = "04/02/2026")
    private LocalDateTime createdAt;
}
