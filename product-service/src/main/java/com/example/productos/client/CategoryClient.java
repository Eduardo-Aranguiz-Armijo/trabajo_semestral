package com.example.productos.client;

import com.example.productos.dto.CategoryResponseDTO;
import com.example.productos.exception.exceptions.CategoryNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component

public class CategoryClient {

        private final WebClient webClient;
        private final HttpServletRequest request;

        public CategoryClient(WebClient.Builder builder, HttpServletRequest request) {

            this.webClient = builder.build();
            this.request = request;
        }

    public CategoryResponseDTO getCategory(Long id) {

        String token = request.getHeader("Authorization");

        return webClient.get()
                .uri("http://ms-category/api/v1/categories/" + id)
                .header("Authorization", token)
                .retrieve()
                .onStatus(HttpStatusCode::isError, response -> response.bodyToMono(String.class).flatMap(error -> Mono.error(new CategoryNotFoundException(error))))
                .bodyToMono(CategoryResponseDTO.class)
                .block();
    }
}

