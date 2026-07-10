package com.example.ms_users.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Entidad que representa respuesta de busqueda de usuario")
public class UserResponseDTO {
    @Schema(description = "identificador unico de usuario", example = "1")
    private Long id;
    private String username;
    @Schema(description = "Rol del usuario. valores permitidos: admin, user", example = "ADMIN")
    private String role;
}
