package com.example.payment.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Schema(description = "Respuesta con la información de un pago")
public class PaymentResponseDTO {

    @Schema(description = "Identificador del pago", example = "1")
    private Long id;

    @Schema(description = "Identificador de la orden asociada", example = "15")
    private Long orderId;

    @Schema(description = "Identificador del cliente asociado", example = "28")
    private Long clientId;

    @Schema(description = "Identificador del método de pago utilizado", example = "2")
    private Long paymentMethodId;

    @Schema(description = "Monto total del pago", example = "150.50")
    private Double amount;

    @Schema(description = "Estado actual del pago", example = "COMPLETED")
    private String status;

    @Schema(description = "Fecha y hora en que se realizó el pago", example = "2026-07-08T10:30:00")
    private LocalDateTime paidAt;
}