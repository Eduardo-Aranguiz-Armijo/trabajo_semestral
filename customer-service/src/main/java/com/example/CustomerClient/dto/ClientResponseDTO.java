package com.example.CustomerClient.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
@Schema(description = "Entidad que representa la respuesta de busqueda de un cliente")
@Data
public class ClientResponseDTO {
    @Schema(description = "identificador unico de cliente", example = "1")
    private Long id;
    private Long userId;
    private String name;
}
