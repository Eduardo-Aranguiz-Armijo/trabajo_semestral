package com.example.catalogos.client;

import com.example.catalogos.dto.ProductResponseDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import java.util.List;
@Component
public class ProductClient {
    private final WebClient webClient;
    private final HttpServletRequest request;

    public ProductClient(WebClient.Builder builder,
                         HttpServletRequest request) {
        this.webClient = builder.build();
        this.request = request;
    }
    public List<ProductResponseDTO> getProductsByCategory(Long categoryId) {
        String token = request.getHeader("Authorization");
        return webClient.get()
                .uri("http://ms-product/api/v1/products/category/" + categoryId)
                .header("Authorization", token)
                .retrieve()
                .bodyToFlux(ProductResponseDTO.class)
                .collectList()
                .block();
    }

}
