package com.example.carrito.client;

import com.example.carrito.dto.ClientResponseDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class ClienteClient {

    private final WebClient webClient;
    private final HttpServletRequest request;

    public ClienteClient(WebClient.Builder builder, HttpServletRequest request) {
        this.webClient = builder.build();
        this.request = request;
    }

    public ClientResponseDTO getByUserId(Long userId)
    {
        String token = request.getHeader("Authorization");
        return webClient.get()
                .uri("http://ms-customer/api/v1/customer/user/" + userId)
                .header("Authorization", token)
                .retrieve()
                .bodyToMono(ClientResponseDTO.class)
                .block();
    }
}