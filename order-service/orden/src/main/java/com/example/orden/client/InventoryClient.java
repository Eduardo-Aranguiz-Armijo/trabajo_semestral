package com.example.orden.client;

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

    public void decreaseStock(
            Long productId,
            Integer quantity
    ) {

        String token =
                request.getHeader("Authorization");

        webClient.put()
                .uri(uriBuilder ->
                        uriBuilder
                                .path("/api/v1/inventory/product/"
                                        + productId
                                        + "/decrease")
                                .queryParam(
                                        "quantity",
                                        quantity
                                )
                                .build()
                )
                .header("Authorization", token)
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }
}
