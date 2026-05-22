package com.example.payment.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PaymentMethodResponseDTO {
    private Long id;

    private Long clienteId;

    private String cardHolder;

    private String cardNumber;

    private String expirationDate;

    private LocalDateTime createdAt;
}
