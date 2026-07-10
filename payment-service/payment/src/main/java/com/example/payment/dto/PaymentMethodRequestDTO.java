package com.example.payment.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "Datos necesarios para registrar un método de pago")
public class PaymentMethodRequestDTO {

    @Schema(description = "Nombre del titular de la tarjeta", example = "Juan Perez")
    @NotBlank
    private String cardHolder;

    @Schema(description = "Número de tarjeta de 16 dígitos", example = "1234567890123456")
    @NotBlank
    @Size(min = 16, max = 16)
    private String cardNumber;

    @Schema(description = "Fecha de expiración en formato MM/AA", example = "12/28")
    @NotBlank
    @Size(min = 5, max = 5)
    private String expirationDate;

    @Schema(description = "Código de seguridad de la tarjeta", example = "123")
    @NotBlank
    @Size(min = 3, max = 4)
    private String cvv;
}
