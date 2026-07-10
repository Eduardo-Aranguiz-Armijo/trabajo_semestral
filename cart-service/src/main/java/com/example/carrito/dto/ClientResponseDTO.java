package com.example.carrito.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class ClientResponseDTO {
    @Schema(description = "identificador de cliente", example = "25")
    private Long id;
    @Schema(description = "identificador de usuario", example = "3")
    private Long userId;
    private String name;
}