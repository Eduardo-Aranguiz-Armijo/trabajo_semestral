package com.example.payment.client;

import com.example.payment.dto.NotificationRequestDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class NotificationClient {

    private final WebClient webClient;
    private final HttpServletRequest request;

    public NotificationClient(WebClient.Builder builder, HttpServletRequest request) {
        this.webClient = builder.build();
        this.request = request;
    }

    public void sendPaymentSuccessNotification(
            Long clienteId,
            Long orderId,
            Long paymentId,
            Double amount
    ) {
        String token = request.getHeader("Authorization");

        NotificationRequestDTO body = new NotificationRequestDTO();
        body.setClienteId(clienteId);
        body.setOrderId(orderId);
        body.setPaymentId(paymentId);
        body.setType("PAYMENT_SUCCESS");
        body.setChannel("IN_APP");
        body.setSubject("Pago confirmado");
        body.setMessage("Su pago por $" + String.format("%.2f", amount) + " fue procesado correctamente.");

        webClient.post()
                .uri("/notificaciones/api/v1/notifications")
                .headers(headers -> {
                    if (token != null) {
                        headers.set("Authorization", token);
                    }
                })
                .bodyValue(body)
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }
}
