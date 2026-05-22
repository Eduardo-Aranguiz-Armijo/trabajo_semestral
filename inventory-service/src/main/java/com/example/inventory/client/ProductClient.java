package com.example.inventory.client;

import com.example.inventory.dto.ProductResponseDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class ProductClient {
    private final WebClient webClient;
    private final HttpServletRequest request;

    public ProductClient(
            WebClient.Builder builder,
            HttpServletRequest request
    ) {

        this.webClient = builder.build();

        this.request = request;
    }

    public ProductResponseDTO getProduct(
            Long id
    ) {

        String token =
                request.getHeader("Authorization");

        return webClient.get()
                .uri("/api/v1/products/" + id)
                .header("Authorization", token)
                .retrieve()
                .bodyToMono(ProductResponseDTO.class)
                .block();
    }
}
