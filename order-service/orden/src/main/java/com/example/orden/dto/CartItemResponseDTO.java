package com.example.orden.dto;

import lombok.Data;

@Data
public class CartItemResponseDTO {
    private Long id;

    private Long cartId;

    private Long productId;

    private Integer cantidad;
}
