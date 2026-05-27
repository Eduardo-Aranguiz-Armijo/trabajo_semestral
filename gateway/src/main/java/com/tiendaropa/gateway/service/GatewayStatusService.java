package com.tiendaropa.gateway.service;

import com.tiendaropa.gateway.config.GatewayRouteRegistry;
import com.tiendaropa.gateway.dto.GatewayStatusResponseDTO;
import com.tiendaropa.gateway.dto.RouteInfoDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class GatewayStatusService {

    private final GatewayRouteRegistry routeRegistry;
    private final WebClient.Builder webClientBuilder;

    public GatewayStatusService(
            GatewayRouteRegistry routeRegistry,
            WebClient.Builder webClientBuilder
    ) {
        this.routeRegistry = routeRegistry;
        this.webClientBuilder = webClientBuilder;
    }

    public GatewayStatusResponseDTO getStatus() {
        GatewayStatusResponseDTO dto = new GatewayStatusResponseDTO();
        dto.setStatus("UP");

        // CORREGIDO: Se quitó el .toString() para enviar el objeto LocalDateTime directamente
        dto.setTimestamp(LocalDateTime.now());

        dto.setMessage("Gateway operativo.");
        return dto;
    }

    public List<RouteInfoDTO> getRoutes() {
        return routeRegistry.getRegisteredRoutes();
    }
}