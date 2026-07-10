package com.example.productos.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;
@Schema(description = "entidad que representa la creacion de un producto y producto inventario")
@Data
public class ProductFullRequestDTO {
    @Size(min = 3, max = 50, message = "Name must be between 3 and 30 characters")
    @NotBlank(message = "Name is required")
    private String name;

    @Size(min = 4, max = 400, message = "Description must be between 4 and 80 characters")
    @NotBlank(message = "Description is required")
    private String description;

    @NotNull(message = "Price is required")
    @Positive(message = "Price must be greater than 0")
    private Double price;

    @Schema(description = "Identificador de categoria de catalogo", example = "1")
    @NotNull(message = "CategoryId is required")
    private Long categoryId;

    @Schema(description = "cantidad de stock de producto", example = "156 unidades")
    @NotNull(message = "Stock is required")
    @Min(value = 1, message = "Stock must be greater than 0")
    private Integer stock;
}