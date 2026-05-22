package com.example.payment.client;

import com.example.payment.dto.OrderResponseDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class OrderClient {
    private final WebClient webClient;

    private final HttpServletRequest request;

    public OrderClient(
            WebClient.Builder builder,
            HttpServletRequest request
    ) {

        this.webClient = builder.build();

        this.request = request;
    }

    public OrderResponseDTO getOrder(
            Long orderId
    ) {

        String token =
                request.getHeader("Authorization");

        return webClient.get()
                .uri("/api/v1/orders/" + orderId)
                .header("Authorization", token)
                .retrieve()
                .bodyToMono(OrderResponseDTO.class)
                .block();
    }
    public void updateStatus(
            Long orderId,
            String estado
    ) {

        String token =
                request.getHeader("Authorization");

        webClient.put()
                .uri(uriBuilder ->
                        uriBuilder
                                .path("/api/v1/orders/"
                                        + orderId
                                        + "/status")
                                .queryParam(
                                        "estado",
                                        estado
                                )
                                .build()
                )
                .header("Authorization", token)
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }
}
