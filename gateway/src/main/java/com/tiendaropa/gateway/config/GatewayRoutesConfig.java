package com.tiendaropa.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

import static org.springframework.cloud.gateway.server.mvc.filter.BeforeFilterFunctions.uri;
import static org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions.route;
import static org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions.http;
import static org.springframework.cloud.gateway.server.mvc.predicate.GatewayRequestPredicates.path;

@Configuration
public class GatewayRoutesConfig {

    @Bean
    public RouterFunction<ServerResponse> gatewayRoutes() {
        return route("auth-service")
                .route(path("/api/v1/auth/**"), http())
                .before(uri("http://localhost:8081"))
                .build()
                .and(route("catalog-service")
                        .route(path("/api/v1/categories/**"), http())
                        .before(uri("http://localhost:8090"))
                        .build())
                .and(route("product-service")
                        .route(path("/api/v1/products/**"), http())
                        .before(uri("http://localhost:8091"))
                        .build())
                .and(route("cart-service")
                        .route(path("/api/v1/cart/**"), http())
                        .before(uri("http://localhost:8083"))
                        .build())
                .and(route("inventory-service")
                        .route(path("/api/v1/inventory/**"), http())
                        .before(uri("http://localhost:8084"))
                        .build())
                .and(route("notification-service")
                        .route(path("/api/v1/notifications/**"), http())
                        .before(uri("http://localhost:8085"))
                        .build())
                .and(route("comprobante-service")
                        .route(path("/api/v1/comprobantes/**"), http())
                        .before(uri("http://localhost:8086"))
                        .build())
                .and(route("payment-service")
                        .route(path("/api/v1/payments/**"), http())
                        .before(uri("http://localhost:8087"))
                        .build())
                .and(route("payment-method-service")
                        .route(path("/api/v1/payment-methods/**"), http())
                        .before(uri("http://localhost:8087"))
                        .build())
                .and(route("order-service")
                        .route(path("/api/v1/orders/**"), http())
                        .before(uri("http://localhost:8088"))
                        .build())
                .and(route("customer-service")
                        .route(path("/api/v1/customer/**"), http())
                        .before(uri("http://localhost:8089"))
                        .build());
    }
}
