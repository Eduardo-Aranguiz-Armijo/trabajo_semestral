package com.example.carrito.dto;

import lombok.Data;

@Data
public class InventoryResponseDTO {
    private Long id;

    private Long productId;

    private Integer stock;
}
