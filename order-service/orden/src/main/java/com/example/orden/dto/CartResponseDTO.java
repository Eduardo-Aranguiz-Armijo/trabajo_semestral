package com.example.orden.dto;

import lombok.Data;

@Data
public class CartResponseDTO {
    private Long id;

    private Long clienteId;

    private String estado;
}
