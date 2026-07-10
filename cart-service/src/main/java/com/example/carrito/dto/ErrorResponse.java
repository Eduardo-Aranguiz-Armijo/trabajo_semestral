package com.example.carrito.dto;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Estructura de respuesta para errores")
public record ErrorResponse(
        @Schema(description = "Fecha y hora del error", example = "2026-06-25T10:15:30")
        LocalDateTime timestamp,
        @Schema(description = "Código de estado HTTP", example = "404")
        int status,
        @Schema(description = "Nombre del error", example = "Not Found")
        String error,
        @Schema(description = "Mensaje detallado", example = "Carrito no encontrado")
        String message,
        @Schema(description = "Ruta donde ocurrió el error", example = "/api/v1/cart/1")
        String path
) {
    public static ErrorResponse of(HttpStatus status, String message, String path) {
        return new ErrorResponse(
                LocalDateTime.now(),
                status.value(),
                status.getReasonPhrase(),
                message,
                path
        );
    }
}
