package com.example.notificaciones.dto;

import com.example.notificaciones.model.NotificationChannel;
import com.example.notificaciones.model.NotificationStatus;
import com.example.notificaciones.model.NotificationType;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class NotificationResponseDTO {

    private Long id;
    private Long clienteId;
    private Long orderId;
    private Long paymentId;
    private NotificationType type;
    private NotificationChannel channel;
    private String subject;
    private String message;
    private NotificationStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime sentAt;
}
