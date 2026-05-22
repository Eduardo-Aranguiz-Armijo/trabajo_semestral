package com.example.comprobante.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ComprobanteResponseDTO {

    private Long id;
    private Long paymentId;
    private Long orderId;
    private Long clienteId;
    private String numeroComprobante;
    private Double amount;
    private String content;
    private LocalDateTime createdAt;
}
