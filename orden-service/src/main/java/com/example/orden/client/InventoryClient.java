package com.example.orden.client;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class InventoryClient {

    private final WebClient webClient;

    private final HttpServletRequest request;

    public InventoryClient(WebClient.Builder builder, HttpServletRequest request) {

        this.webClient = builder
                .baseUrl("http://ms-inventory")
                .build();

        this.request = request;
    }

    // =========================================
    // DECREASE STOCK
    // =========================================

    public void decreaseStock(Long productId, Integer quantity) {

        String token = request.getHeader("Authorization");

        webClient.put()
                .uri(uriBuilder ->
                        uriBuilder.path("/api/v1/inventory/product/{id}/decrease")
                                .queryParam("quantity", quantity).build(productId))
                .header("Authorization", token)
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }

    // =========================================
    // INCREASE STOCK
    // =========================================

    public void increaseStock(Long productId, Integer quantity) {

        String token = request.getHeader("Authorization");

        webClient.put()
                .uri(uriBuilder -> uriBuilder.path("/api/v1/inventory/product/{id}/increase").queryParam("quantity", quantity).build(productId))
                .header("Authorization", token)
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }
}