package com.example.catalogos.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "entidad que representa la respuesta de una categoria de productos")
public class CategoryResponseDTO {
    @Schema(description = "identificador de una categoria",example = "1")
    private Long id;
    private String name;
    private String description;
}
