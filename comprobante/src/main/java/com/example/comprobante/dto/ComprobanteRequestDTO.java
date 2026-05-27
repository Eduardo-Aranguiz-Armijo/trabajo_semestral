package com.example.comprobante.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class ComprobanteRequestDTO {

    @NotNull
    private Long paymentId;

    @NotNull
    private Long orderId;

    @NotNull
    private Long clienteId;

    @NotNull
    @Positive
    private Double amount;
}
