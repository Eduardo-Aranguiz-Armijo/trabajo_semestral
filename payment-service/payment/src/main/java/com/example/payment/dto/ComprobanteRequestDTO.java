package com.example.payment.dto;

import lombok.Data;

@Data
public class ComprobanteRequestDTO {

    private Long paymentId;
    private Long orderId;
    private Long clienteId;
    private Double amount;
}
