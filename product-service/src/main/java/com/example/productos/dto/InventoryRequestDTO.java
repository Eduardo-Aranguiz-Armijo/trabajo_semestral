package com.example.productos.dto;

import lombok.Data;

@Data
public class InventoryRequestDTO {
    private Long productId;

    private Integer stock;
}
