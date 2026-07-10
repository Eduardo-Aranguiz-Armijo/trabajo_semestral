package com.example.orden.client;

import com.example.orden.dto.CartItemResponseDTO;
import com.example.orden.dto.CartRequestDTO;
import com.example.orden.dto.CartResponseDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Component
public class CartClient {

    private final WebClient webClient;

    private final HttpServletRequest request;

    public CartClient(WebClient.Builder builder, HttpServletRequest request) {

        this.webClient = builder
                .baseUrl("http://ms-cart")
                .build();

        this.request = request;
    }

    // =========================================
    // GET MY CART
    // =========================================

    public CartResponseDTO getMyCart() {

        String token = request.getHeader("Authorization");

        return webClient.get()
                .uri("/api/v1/cart/me")
                .header("Authorization", token)
                .retrieve()
                .bodyToMono(CartResponseDTO.class)
                .block();
    }

    // =========================================
    // GET MY ITEMS
    // =========================================

    public List<CartItemResponseDTO> getMyItems() {

        String token = request.getHeader("Authorization");

        return webClient.get()
                .uri("/api/v1/cart/items/me")
                .header("Authorization", token)
                .retrieve()
                .bodyToFlux(CartItemResponseDTO.class)
                .collectList()
                .block();
    }

    // =========================================
    // UPDATE CART STATUS
    // =========================================

    public void updateCartStatus(Long cartId, String status) {
        String token = request.getHeader("Authorization");

        CartRequestDTO requestDTO = new CartRequestDTO();

        requestDTO.setStatus(status);

        webClient.put()
                .uri("/api/v1/cart/" + cartId)
                .header("Authorization", token)
                .bodyValue(requestDTO)
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }
}