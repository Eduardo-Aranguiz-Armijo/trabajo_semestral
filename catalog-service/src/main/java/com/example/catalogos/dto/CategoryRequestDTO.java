package com.example.catalogos.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "entidad que representa el registro de una categoria de productos")
public class CategoryRequestDTO {
    @NotBlank(message = "Name category is required")
    @Size(min = 5, max = 15, message = "Catalog must be between 5 and 15 characters")
    private String name;

    @NotBlank(message = "Description  is required")
    @Size(min = 5, max = 40, message = "Description must be between 5 and 40 characters")
    private String description;
}
