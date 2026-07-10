package com.example.orden.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "entidad que representa el cambio de estatus de un carrito")
public class CartRequestDTO {
    @Schema(description = "estatus de carrito, valores: CHECKOUT, CANCELLED, ACTIVE", example = "CHECKOUT")
    @NotBlank(message = "Status is required")
    private String status;
}