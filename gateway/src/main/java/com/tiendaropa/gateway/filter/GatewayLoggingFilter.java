package com.tiendaropa.gateway.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class GatewayLoggingFilter extends OncePerRequestFilter {

    @Value("${gateway.logging.enabled:true}")
    private boolean loggingEnabled;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        long start = System.currentTimeMillis();

        try {
            filterChain.doFilter(request, response);
        } finally {
            if (loggingEnabled && !request.getRequestURI().startsWith("/api/v1/gateway")) {
                long duration = System.currentTimeMillis() - start;
                System.out.printf(
                        "[Gateway] %s %s -> %d (%dms)%n",
                        request.getMethod(),
                        request.getRequestURI(),
                        response.getStatus(),
                        duration
                );
            }
        }
    }
}
