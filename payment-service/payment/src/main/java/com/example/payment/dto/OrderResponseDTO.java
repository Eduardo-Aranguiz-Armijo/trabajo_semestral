package com.example.payment.dto;

import lombok.Data;

@Data
public class OrderResponseDTO {
    private Long id;

    private Long clienteId;

    private Long cartId;

    private Double total;

    private String estado;
}
