package com.example.orden.client;

import com.example.orden.dto.CartItemResponseDTO;
import com.example.orden.dto.CartResponseDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Component
public class CartClient {
    private final WebClient webClient;
    private final HttpServletRequest request;

    public CartClient(
            WebClient.Builder builder,
            HttpServletRequest request
    ) {

        this.webClient = builder.build();

        this.request = request;
    }

    public CartResponseDTO getMyCart() {

        String token =
                request.getHeader("Authorization");

        return webClient.get()
                .uri("/api/v1/cart/me")
                .header("Authorization", token)
                .retrieve()
                .bodyToMono(CartResponseDTO.class)
                .block();
    }

    public List<CartItemResponseDTO> getMyItems() {

        String token =
                request.getHeader("Authorization");

        return webClient.get()
                .uri("/api/v1/cart/items")
                .header("Authorization", token)
                .retrieve()
                .bodyToFlux(CartItemResponseDTO.class)
                .collectList()
                .block();
    }
}
