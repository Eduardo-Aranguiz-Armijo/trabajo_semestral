package com.example.notificaciones.client;

import com.example.notificaciones.dto.OrderResponseDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class OrderClient {

    private final WebClient webClient;
    private final HttpServletRequest request;

    public OrderClient(WebClient.Builder builder, HttpServletRequest request) {
        this.webClient = builder.build();
        this.request = request;
    }

    public OrderResponseDTO getOrder(Long orderId) {
        String token = request.getHeader("Authorization");

        return webClient.get()
                .uri("/api/v1/orders/" + orderId)
                .headers(headers -> {
                    if (token != null) {
                        headers.set("Authorization", token);
                    }
                })
                .retrieve()
                .bodyToMono(OrderResponseDTO.class)
                .block();
    }
}
