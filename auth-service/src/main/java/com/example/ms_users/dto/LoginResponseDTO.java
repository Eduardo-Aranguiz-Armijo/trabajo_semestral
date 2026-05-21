package com.example.ms_users.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponseDTO {
    //es el json que nos respondera el servidor a nosotros luego de logearnos
    private String token;
    private String username;
    private String role;
}
