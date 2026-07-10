package com.example.ms_users.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
@Schema(description = "Informacion del registro enviada a microservicios customer-service")
@Data
public class CustomerRequestDTO {
    //es el json que se enviara al otro microservicio
    private String name;
    @Schema(description = "Rut sin puntos y con guion", example = "205435453")
    private String rut;
    private String email;
    private String address;
    private String phone;

    @Schema(description = "id de user que conecta con cliente (de customer-service)", example = "3")
    private Long userId;
}
