package com.example.orden.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OrderResponseDTO {
    private Long id;

    private Long clienteId;

    private Long cartId;

    private Double total;

    private String estado;

    private LocalDateTime createdAt;
}
