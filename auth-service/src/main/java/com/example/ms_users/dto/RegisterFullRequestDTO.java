package com.example.ms_users.dto;

import lombok.Data;

@Data
public class RegisterFullRequestDTO {
    //es el JSON completo que enviaremos a este microservicio
    private String username;
    private String password;
    private String role;

    private String nombre;
    private String rut;
    private String correo;
    private String direccion;
    private String telefono;

}
