package com.example.carrito.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
@Data
@Schema(description = "Respuesta con la información de un producto")
public class ProductResponseDTO {
    @Schema(description = "identificador de producto", example = "25")
    private Long id;
    private String name;
    private String description;
    private Double price;

    @Schema(description = "Cantidad disponible en inventario", example = "25")
    private Integer stock;

    @Schema(description = "Identificador de la categoría a la que pertenece el producto", example = "3")
    private Long categoryId;
}