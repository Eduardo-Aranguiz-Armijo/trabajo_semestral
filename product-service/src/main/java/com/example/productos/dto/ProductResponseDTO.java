package com.example.productos.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
@Schema(description = "entidad que representa la respuesta de un producto")
@Data
public class ProductResponseDTO {
    @Schema(description = "Identificador de producto", example = "1")
    private Long id;
    private String name;
    private String description;
    private Double price;
    @Schema(description = "Identificador de categoria de catalogo", example = "1")
    private Long categoryId;
}
