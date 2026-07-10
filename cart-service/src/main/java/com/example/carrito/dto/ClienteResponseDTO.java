package com.example.carrito.dto;

import lombok.Data;

import io.swagger.v3.oas.annotations.media.Schema;

@Data
@Schema(description = "Datos de respuesta del cliente")
public class ClienteResponseDTO {

    @Schema(description = "ID del cliente", example = "1")
    private Long id;
    
    @Schema(description = "ID del usuario asociado", example = "1")
    private Long userId;
    
    @Schema(description = "Nombre completo del cliente", example = "Eduardo Pérez")
    private String nombre;
}