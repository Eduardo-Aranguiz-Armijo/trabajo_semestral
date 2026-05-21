package com.tiendaropa.gateway.controller;

import com.tiendaropa.gateway.dto.GatewayStatusResponseDTO;
import com.tiendaropa.gateway.dto.RouteInfoDTO;
import com.tiendaropa.gateway.service.GatewayStatusService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/gateway")
public class GatewayController {

    private final GatewayStatusService statusService;

    public GatewayController(GatewayStatusService statusService) {
        this.statusService = statusService;
    }

    @GetMapping("/status")
    public ResponseEntity<GatewayStatusResponseDTO> status() {
        return ResponseEntity.ok(statusService.getStatus());
    }

    @GetMapping("/routes")
    public ResponseEntity<List<RouteInfoDTO>> routes() {
        return ResponseEntity.ok(statusService.getRoutes());
    }
}