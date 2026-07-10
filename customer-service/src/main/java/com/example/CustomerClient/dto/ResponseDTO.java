package com.example.CustomerClient.dto;

import lombok.Data;

import io.swagger.v3.oas.annotations.media.Schema;

@Data
@Schema(description = "Respuesta genérica del servidor")
public class ResponseDTO {
    @Schema(description = "Mensaje de respuesta", example = "Operación exitosa")
    private String message;
}
