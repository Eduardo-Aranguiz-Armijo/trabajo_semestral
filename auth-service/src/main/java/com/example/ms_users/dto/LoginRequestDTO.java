package com.example.ms_users.dto;

import lombok.Data;

@Data
public class LoginRequestDTO {
    //es el json que enviaremos nosotros para logearnos
    private String username;
    private String password;
}
