package com.example.payment.dto;

import lombok.Data;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;

@Data
@Schema(description = "Datos de respuesta de un pago procesado")
public class PaymentResponseDTO {
    @Schema(description = "ID del pago", example = "1")
    private Long id;

    @Schema(description = "ID de la orden pagada", example = "1")
    private Long orderId;

    @Schema(description = "ID del cliente que realizó el pago", example = "1")
    private Long clienteId;

    @Schema(description = "ID del método de pago utilizado", example = "1")
    private Long paymentMethodId;

    @Schema(description = "Monto pagado", example = "999.99")
    private Double amount;

    @Schema(description = "Estado del pago", example = "COMPLETADO")
    private String status;

    @Schema(description = "Fecha y hora del pago", example = "2026-06-25T10:15:30")
    private LocalDateTime paidAt;
}
