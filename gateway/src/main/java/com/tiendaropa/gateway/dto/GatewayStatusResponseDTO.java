package com.tiendaropa.gateway.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GatewayStatusResponseDTO {
    private String status;
    private LocalDateTime timestamp; // Conservamos LocalDateTime que ya funciona
    private String message;          // AGREGADO: Esto solucionará el error de setMessage
}