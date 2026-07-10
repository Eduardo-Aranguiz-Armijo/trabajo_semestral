package com.example.payment.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "entidad que representa la respuesta de informacion de un cliente")
public class ClientResponseDTO {
    @Schema(description = "identificador de cliente", example = "25")
    private Long id;
    @Schema(description = "identificador de usuario", example = "1")
    private Long userId;

    private String nombre;
    @Schema(description = "Correo del cliente", example = "juanperez@gmail.com")
    private String email;
}
