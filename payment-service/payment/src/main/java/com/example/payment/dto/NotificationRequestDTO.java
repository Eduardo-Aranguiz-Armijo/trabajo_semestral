package com.example.payment.dto;

import lombok.Data;

@Data
public class NotificationRequestDTO {

    private Long clienteId;
    private Long orderId;
    private Long paymentId;
    private String type;
    private String channel;
    private String subject;
    private String message;
}
