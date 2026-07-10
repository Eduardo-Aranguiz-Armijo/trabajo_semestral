package com.example.inventory.dto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Schema(description = "entidad que representa la respuesta de un producto de inventario")
public class InventoryResponseDTO {
    @Schema(description = "identificador unico del producto de inventario", example = "1")
    private Long id;
    @Schema(description = "identificador unico del producto", example = "1")
    private Long productId;
    @Schema(description = "cantidad de stock del producto", example = "175 unidades")
    private Integer stock;
    @Schema(description = "fecha de creacion", example = "04/05/2026")
    private LocalDateTime updatedAt;
}
