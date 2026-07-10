package com.example.payment.client;

import com.example.payment.dto.ComprobanteRequestDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class ComprobanteClient {

    private final WebClient webClient;
    private final HttpServletRequest request;

    public ComprobanteClient(WebClient.Builder builder, HttpServletRequest request) {
        this.webClient = builder.build();
        this.request = request;
    }

    public void generateComprobante(
            Long paymentId,
            Long orderId,
            Long clienteId,
            Double amount
    ) {
        String token = request.getHeader("Authorization");

        ComprobanteRequestDTO body = new ComprobanteRequestDTO();
        body.setPaymentId(paymentId);
        body.setOrderId(orderId);
        body.setClienteId(clienteId);
        body.setAmount(amount);

        webClient.post()
                .uri("/comprobante/api/v1/comprobantes")
                .headers(headers -> {
                    if (token != null) {
                        headers.set("Authorization", token);
                    }
                })
                .bodyValue(body)
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }
}
