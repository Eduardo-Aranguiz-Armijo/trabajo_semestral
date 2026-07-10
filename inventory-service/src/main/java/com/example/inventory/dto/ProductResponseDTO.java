package com.example.inventory.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
@Schema(description = "Entidad que representa la respuesta de un producto")
@Data
public class ProductResponseDTO {
    @Schema(description = "identificador unico del producto", example = "1")
    private Long id;

    private String name;

    private Double price;
}
