package com.example.payment.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "Entidad que representa a un pago")
public class PaymentRequestDTO {
    //se agrega orden manual, ya que puede contar con una orden o mas +  agrega un metodo de pago manual ya que un cliente puede tener uno o varios metodos de pago
    @Schema(description = "identificador de orden", example = "30")
    @NotNull
    private Long orderId;
    @NotNull
    @Schema(description = "identificador de un metodo de pago", example = "2")
    private Long paymentMethodId;
}
