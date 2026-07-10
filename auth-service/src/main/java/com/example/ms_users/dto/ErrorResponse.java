package com.example.ms_users.dto;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Estructura estándar para respuestas de error")
public record ErrorResponse(
        @Schema(description = "Fecha y hora del error", example = "2023-10-25T10:00:00.123")
        LocalDateTime timestamp,
        @Schema(description = "Código de estado HTTP", example = "400")
        int status,
        @Schema(description = "Tipo de error HTTP", example = "Bad Request")
        String error,
        @Schema(description = "Mensaje detallado del error", example = "El usuario ya existe")
        String message,
        @Schema(description = "Ruta donde ocurrió el error", example = "/api/v1/auth/register")
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
