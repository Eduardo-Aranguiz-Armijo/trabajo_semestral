package com.example.payment.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import io.swagger.v3.oas.annotations.media.Schema;

@Data
@Schema(description = "Datos para registrar un método de pago")
public class PaymentMethodRequestDTO {
    @NotBlank
    @Schema(description = "Nombre del titular de la tarjeta", example = "Juan Perez")
    private String cardHolder;

    @NotBlank
    @Size(min = 16, max = 16)
    @Schema(description = "Número de la tarjeta (16 dígitos)", example = "1234567890123456")
    private String cardNumber;

    @NotBlank
    @Size(min = 5, max = 5)
    @Schema(description = "Fecha de expiración (MM/YY)", example = "12/28")
    private String expirationDate;

    @NotBlank
    @Size(min = 3, max = 4)
    @Schema(description = "Código de seguridad (CVV)", example = "123")
    private String cvv;
}
