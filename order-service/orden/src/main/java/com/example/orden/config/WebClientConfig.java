package com.example.orden.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient.Builder webClientBuilder(
            @Value("${app.gateway.url:http://localhost:8080}") String gatewayUrl
    ) {
        return WebClient.builder().baseUrl(gatewayUrl);
    }
}
