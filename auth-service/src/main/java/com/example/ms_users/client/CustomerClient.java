package com.example.ms_users.client;

import com.example.ms_users.dto.CustomerRequestDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class CustomerClient {

    private final WebClient webClient;

    public CustomerClient(WebClient webClient) {
        this.webClient = webClient;
    }

    public void createCustomer(CustomerRequestDTO request, Long userId) {{
            webClient.post()
                    .uri("",userId)
                    .bodyValue(request)
                    .retrieve()
                    .bodyToMono(Void.class)
                    .block();
        }
    }
}
