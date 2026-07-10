package com.example.ms_users.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "Entidad que representa el formulario de registro")
public class RegisterFullRequestDTO {

    @NotBlank(message = "Username is required")
    @Size(min = 4, max = 20, message = "Username must be between 4 and 20 characters")
    private String username;

    @Schema(description = "contrasena del usuario, minimo 8 caracteres", example = "mypassword123")
    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must have at least 6 characters")
    private String password;
    @Schema(description = "Rol del usuario. valores permitidos: admin, user", example = "ADMIN")
    private String role;

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Rut is required")
    private String rut;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Address is required")
    private String address;

    @NotBlank(message = "Phone is required")
    private String phone;
}
