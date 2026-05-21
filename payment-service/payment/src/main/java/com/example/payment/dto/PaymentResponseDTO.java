package com.example.payment.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PaymentResponseDTO {
    private Long id;

    private Long orderId;

    private Long clienteId;

    private Long paymentMethodId;

    private Double amount;

    private String status;

    private LocalDateTime paidAt;
}
