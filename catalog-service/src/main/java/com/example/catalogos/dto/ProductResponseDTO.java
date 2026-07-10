package com.example.catalogos.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "entidad que representa la respuesta de un producto")
public class ProductResponseDTO {
    @Schema(description = "identificador de una categoria",example = "1")
    private Long id;
    private String name;
    private Double price;
}
