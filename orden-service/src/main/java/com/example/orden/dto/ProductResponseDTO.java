package com.example.orden.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
@Schema(description = "entidad que representa la respuesta de un prodcuto")
@Data
public class ProductResponseDTO {
    @Schema(description = "identificador de producto", example = "2")
    private Long id;
    private String name;
    private Double price;
}
