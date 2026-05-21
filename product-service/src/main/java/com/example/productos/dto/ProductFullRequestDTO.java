package com.example.productos.dto;

import lombok.Data;
@Data
public class ProductFullRequestDTO {
    private String name;

    private String description;

    private Double price;

    private Long categoryId;

    private Integer stock;
}
