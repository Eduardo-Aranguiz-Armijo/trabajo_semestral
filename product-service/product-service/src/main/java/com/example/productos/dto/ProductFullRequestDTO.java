package com.example.productos.dto;

import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;

@Data
@Schema(description = "Datos para crear un producto y su inventario")
public class ProductFullRequestDTO {
    @Schema(description = "Nombre del producto", example = "Laptop Pro")
    private String name;

    @Schema(description = "Descripción del producto", example = "Laptop de 16GB RAM y 512GB SSD")
    private String description;

    @Schema(description = "Precio del producto", example = "999.99")
    private Double price;

    @Schema(description = "ID de la categoría a la que pertenece", example = "1")
    private Long categoryId;

    @Schema(description = "Stock inicial en inventario", example = "50")
    private Integer stock;
}
