package com.example.productos.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "entidad que representa la respuesta de una categoria de inventario")
public class CategoryResponseDTO {
    @Schema(description = "Identificador de categoria de catalogo", example = "1")
    private Long id;
    private String name;
    private String description;
}
