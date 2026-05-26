package com.tiendaropa.gateway.config;

import com.tiendaropa.gateway.dto.RouteInfoDTO;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GatewayRouteRegistry {

public List<RouteInfoDTO> getRegisteredRoutes() {
        return List.of(
                // 1. Auth
                route("ms-users", "http://localhost:8081", "/api/v1/auth/**"),
                
                // 2. Carrito
                route("carrito", "http://localhost:8082", "/api/v1/cart/**"),
                
                // 3. Catálogos
                route("catalogos", "http://localhost:8083", "/api/v1/categories/**"),
                
                // 4. Comprobante
                route("comprobante", "http://localhost:8084", "/api/v1/comprobantes/**"),
                
                // 5. Clientes
                route("CustomerClient", "http://localhost:8085", "/api/v1/customer/**"),
                
                // 6. Inventario
                route("inventory", "http://localhost:8086", "/api/v1/inventory/**"),
                
                // 7. Notificaciones
                route("notificaciones", "http://localhost:8087", "/api/v1/notifications/**"),
                
                // 8. Órdenes
                route("orden", "http://localhost:8088", "/api/v1/orders/**"),
                
                // 9. Pagos
                route("payment", "http://localhost:8089", "/api/v1/payment/**"),
                route("payment", "http://localhost:8089", "/api/v1/payment-methods/**"),
                
                // 10. Productos
                route("productos", "http://localhost:8090", "/api/v1/productos/**")
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
