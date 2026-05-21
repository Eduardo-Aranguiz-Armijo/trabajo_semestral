package com.example.payment.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PaymentRequestDTO {
    @NotNull
    private Long orderId;
    @NotNull
    private Long paymentMethodId;
}
