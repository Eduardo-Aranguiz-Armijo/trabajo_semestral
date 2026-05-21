package com.example.carrito.dto;

import lombok.Data;

@Data
public class CartItemRequestDTO {
    private Long productId;
    private Integer cantidad;
}
