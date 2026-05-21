package com.example.CustomerClient.dto;

import lombok.Data;

@Data
public class ClientRequestDTO{
    //es el DTO que recibiremos del otro microservicio
    private String nombre;
    private String rut;
    private String correo;
    private String direccion;
    private String telefono;
    private Long userId;
}
