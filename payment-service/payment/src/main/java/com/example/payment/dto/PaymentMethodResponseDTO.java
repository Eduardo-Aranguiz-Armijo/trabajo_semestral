package com.example.payment.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Schema(description = "Respuesta con la información de un método de pago")
public class PaymentMethodResponseDTO {

    @Schema(description = "Identificador del método de pago", example = "2")
    private Long id;

    @Schema(description = "Identificador del cliente asociado", example = "28")
    private Long clientId;

    @Schema(description = "Nombre del titular de la tarjeta", example = "Juan Perez")
    private String cardHolder;

    @Schema(description = "Número de tarjeta registrado", example = "**** **** **** 1234")
    private String cardNumber;

    @Schema(description = "Fecha de expiración de la tarjeta", example = "12/28")
    private String expirationDate;

    @Schema(description = "Fecha de creación del método de pago", example = "2026-07-08T10:30:00")
    private LocalDateTime createdAt;
}