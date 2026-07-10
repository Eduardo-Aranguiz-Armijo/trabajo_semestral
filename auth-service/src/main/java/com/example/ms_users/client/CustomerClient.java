package com.example.ms_users.client;

import com.example.ms_users.dto.CustomerRequestDTO;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;


@Service
public class CustomerClient {

    private final WebClient.Builder webClientBuilder;

    public CustomerClient(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    public void createCustomer(CustomerRequestDTO request, Long userId) {
        webClientBuilder.build().post()
                .uri("http://ms-customer/api/v1/customer")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }
}
