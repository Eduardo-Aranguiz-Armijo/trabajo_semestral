package com.example.ms_users.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "Entidad que representa el formulario de inicio de sesion")
public class LoginRequestDTO {
    //es el json que enviaremos nosotros para logearnos
    @NotBlank(message = "Username is required")
    @Size(min = 4, max = 20, message = "Username must be between 4 and 20 characters")
    private String username;

    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must have at least 6 characters")
    @Schema(description = "contrasena del usuario, minimo 8 caracteres", example = "mypassword123")
    private String password;
}
