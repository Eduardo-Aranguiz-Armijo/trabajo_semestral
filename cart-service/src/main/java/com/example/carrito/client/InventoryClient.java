package com.example.carrito.client;

import com.example.carrito.dto.InventoryResponseDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class InventoryClient {
    private final WebClient webClient;

    private final HttpServletRequest request;

    public InventoryClient(
            WebClient.Builder builder,
            HttpServletRequest request
    ) {

        this.webClient = builder.build();

        this.request = request;
    }

    public InventoryResponseDTO getInventory(
            Long productId
    ) {

        String token =
                request.getHeader("Authorization");

        return webClient.get()
                .uri("/api/v1/inventory/product/"
                        + productId)
                .header("Authorization", token)
                .retrieve()
                .bodyToMono(
                        InventoryResponseDTO.class
                )
                .block();
    }
}
