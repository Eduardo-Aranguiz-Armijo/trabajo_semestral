package com.example.carrito.dto;

import lombok.Data;

@Data
public class CartItemResponseDTO {
    private Long id;
    private Long cartId;
    private Long productId;
    private Integer cantidad;
}
