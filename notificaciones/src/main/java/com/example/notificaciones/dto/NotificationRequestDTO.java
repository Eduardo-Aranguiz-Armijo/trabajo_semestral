package com.example.notificaciones.dto;

import com.example.notificaciones.model.NotificationChannel;
import com.example.notificaciones.model.NotificationType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class NotificationRequestDTO {

    @NotNull
    private Long clienteId;

    private Long orderId;

    private Long paymentId;

    @NotNull
    private NotificationType type;

    @NotNull
    private NotificationChannel channel;

    @NotBlank
    private String subject;

    private String message;
}
