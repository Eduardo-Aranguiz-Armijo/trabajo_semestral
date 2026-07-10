package com.example.productos.dto;

import lombok.Data;

import io.swagger.v3.oas.annotations.media.Schema;

@Data
@Schema(description = "Datos de respuesta de una categoría")
public class CategoryResponseDTO {
    @Schema(description = "ID de la categoría", example = "1")
    private Long id;
    
    @Schema(description = "Nombre de la categoría", example = "Electrónica")
    private String name;
    
    @Schema(description = "Descripción de la categoría", example = "Dispositivos electrónicos y computación")
    private String description;
}
