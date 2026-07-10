package com.example.payment.dto;

import lombok.Data;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;

@Data
@Schema(description = "Datos de respuesta de un método de pago")
public class PaymentMethodResponseDTO {
    @Schema(description = "ID del método de pago", example = "1")
    private Long id;

    @Schema(description = "ID del cliente propietario", example = "1")
    private Long clienteId;

    @Schema(description = "Titular de la tarjeta", example = "Juan Perez")
    private String cardHolder;

    @Schema(description = "Últimos 4 dígitos de la tarjeta (enmascarada)", example = "**** **** **** 3456")
    private String cardNumber;

    @Schema(description = "Fecha de expiración", example = "12/28")
    private String expirationDate;

    @Schema(description = "Fecha de registro", example = "2026-06-25T10:15:30")
    private LocalDateTime createdAt;
}
