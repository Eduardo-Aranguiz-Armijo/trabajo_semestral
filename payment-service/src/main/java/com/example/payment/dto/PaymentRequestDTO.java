package com.example.payment.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import io.swagger.v3.oas.annotations.media.Schema;

@Data
@Schema(description = "Datos para procesar un pago")
public class PaymentRequestDTO {
    @NotNull
    @Schema(description = "ID de la orden a pagar", example = "1")
    private Long orderId;
    @NotNull
    @Schema(description = "ID del método de pago a utilizar", example = "1")
    private Long paymentMethodId;
}
