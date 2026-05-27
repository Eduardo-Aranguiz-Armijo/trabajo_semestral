package com.example.productos.client;

import com.example.productos.dto.InventoryRequestDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
@Component
public class InventoryClient {

    private final WebClient webClient;
    private final HttpServletRequest request;

    public InventoryClient(WebClient.Builder builder,
                          HttpServletRequest request) {

        this.webClient = builder.build();

        this.request = request;
    }

    public void createInventory(
            InventoryRequestDTO requestDto
    ) {

        String token =
                request.getHeader("Authorization");

        webClient.post()
                .uri("/api/v1/inventory")
                .header("Authorization", token)
                .bodyValue(requestDto)
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }
}
