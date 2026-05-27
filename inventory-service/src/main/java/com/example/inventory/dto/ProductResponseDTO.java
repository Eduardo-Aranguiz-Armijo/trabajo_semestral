package com.example.inventory.dto;

import lombok.Data;

@Data
public class ProductResponseDTO {
    private Long id;

    private String name;

    private Double price;
}
