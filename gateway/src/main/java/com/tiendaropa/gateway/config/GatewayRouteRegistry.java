package com.tiendaropa.gateway.config;

import com.tiendaropa.gateway.dto.RouteInfoDTO;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GatewayRouteRegistry {

    public List<RouteInfoDTO> getRegisteredRoutes() {
        return List.of(
                route("auth-service", "http://localhost:8081", "/api/v1/auth/**"),
                route("catalog-service", "http://localhost:8090", "/api/v1/categories/**"),
                route("product-service", "http://localhost:8091", "/api/v1/products/**"),
                route("cart-service", "http://localhost:8083", "/api/v1/cart/**"),
                route("inventory-service", "http://localhost:8084", "/api/v1/inventory/**"),
                route("notification-service", "http://localhost:8085", "/api/v1/notifications/**"),
                route("comprobante-service", "http://localhost:8086", "/api/v1/comprobantes/**"),
                route("payment-service", "http://localhost:8087", "/api/v1/payments/**"),
                route("payment-method-service", "http://localhost:8087", "/api/v1/payment-methods/**"),
                route("order-service", "http://localhost:8088", "/api/v1/orders/**"),
                route("customer-service", "http://localhost:8089", "/api/v1/customer/**")
        );
    }

    private RouteInfoDTO route(String id, String uri, String pathPattern) {
        RouteInfoDTO dto = new RouteInfoDTO();
        dto.setId(id);
        dto.setUri(uri);
        dto.setPathPattern(pathPattern);
        dto.setStatus("UNKNOWN");
        return dto;
    }
}
