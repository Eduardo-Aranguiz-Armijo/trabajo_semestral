package com.example.payment.dto;

import lombok.Data;

@Data
public class ClientResponseDTO {
    private Long id;

    private Long userId;

    private String nombre;

    private String email;
}
