package com.example.CustomerClient.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "Entidad que representa el formulario de registro de user")
public class ClientRequestDTO {

    @NotBlank(message = "Name is required")
    private String name;
    @Schema(description = "Rut sin puntos y con guion", example = "205435453")
    @NotBlank(message = "Rut is required")
    private String rut;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Address is required")
    private String address;

    @Schema(description = "identificador unico de usuario", example = "1")
    @NotBlank(message = "Phone is required")
    private String phone;

    @NotNull(message = "UserId is required")
    private Long userId;
}