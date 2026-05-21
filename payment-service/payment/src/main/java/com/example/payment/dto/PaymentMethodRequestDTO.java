package com.example.payment.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PaymentMethodRequestDTO {
    @NotBlank
    private String cardHolder;

    @NotBlank
    @Size(min = 16, max = 16)
    private String cardNumber;

    @NotBlank
    @Size(min = 5, max = 5)
    private String expirationDate;

    @NotBlank
    @Size(min = 3, max = 4)
    private String cvv;
}
