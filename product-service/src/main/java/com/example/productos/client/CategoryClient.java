package com.example.productos.client;

import com.example.productos.dto.CategoryResponseDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
@Component
@Service
public class CategoryClient {

        private final WebClient webClient;
        private final HttpServletRequest request;

        public CategoryClient(WebClient.Builder builder,
                              HttpServletRequest request) {

            this.webClient = builder.build();

            this.request = request;
        }

        public CategoryResponseDTO getCategory(Long id) {

            String token = request.getHeader("Authorization");

            return webClient.get()
                    .uri("/api/v1/categories/" + id)
                    .header("Authorization", token)
                    .retrieve()
                    .bodyToMono(CategoryResponseDTO.class)
                    .block();
        }

    }

