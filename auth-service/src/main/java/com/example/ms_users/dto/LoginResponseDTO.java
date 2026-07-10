package com.example.ms_users.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Entidad que representa la respuesta de inicio de sesion")
public class LoginResponseDTO {
    //es el json que nos respondera el servidor a nosotros luego de logearnos
    private String token;
    private String username;
    @Schema(description = "Rol del usuario. valores permitidos: admin, user", example = "ADMIN")
    private String role;
}
