package com.example.orden.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "entidad que representa respuesta de los datosde un cliente")
public class ClientResponseDTO {
    @Schema(description = "identificador de cliente ", example = "4")
    private Long id;
    @Schema(description = "identificador de usuario", example = "2")
    private Long userId;
    private String nombre;
    @Schema(description = "correo de cliente", example = "juanperez@gmail.com")
    private String correo;

    private String telefono;
    private String direccion;
    private String rut;
}