package com.example.inventory.dto;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class InventoryResponseDTO {
    private Long id;

    private Long productId;

    private Integer stock;

    private LocalDateTime updatedAt;
}
