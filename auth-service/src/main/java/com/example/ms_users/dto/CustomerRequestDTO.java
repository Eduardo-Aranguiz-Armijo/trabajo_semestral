package com.example.ms_users.dto;

import lombok.Data;

@Data
public class CustomerRequestDTO {
    //es el json que se enviara al otro microservicio
    private String nombre;
    private String rut;
    private String correo;
    private String direccion;
    private String telefono;


    private Long userId;
}
