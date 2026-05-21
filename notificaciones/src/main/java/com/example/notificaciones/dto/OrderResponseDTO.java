package com.example.notificaciones.dto;

import lombok.Data;

@Data
public class OrderResponseDTO {

    private Long id;
    private Long clienteId;
    private String estado;
}
