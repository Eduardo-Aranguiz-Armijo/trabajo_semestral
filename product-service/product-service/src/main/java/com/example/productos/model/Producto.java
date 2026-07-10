package com.example.productos.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

@Data
@Entity
@Table(name = "products")
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Entidad que representa un producto en el sistema")
public class Producto extends RepresentationModel<Producto> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(
            description = "Identificador único del producto",
            example = "1",
            accessMode = Schema.AccessMode.READ_ONLY
    )
    private Long id;

    @Column(nullable = false)
    @Schema(
            description = "Nombre del producto",
            example = "Laptop HP Pavilion",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String name;

    @Column(length = 500)
    @Schema(
            description = "Descripción detallada del producto",
            example = "Laptop con procesador Intel Core i5, 8GB RAM, 256GB SSD",
            maxLength = 500
    )
    private String description;

    @Column(nullable = false)
    @Schema(
            description = "Precio del producto",
            example = "799.99",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private Double price;

    @Column(nullable = false)
    @Schema(
            description = "ID de la categoría a la que pertenece el producto",
            example = "5",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private Long categoryId; // FK lógica (Catalog Service)

    @Column(nullable = false)
    @Schema(
            description = "Indica si el producto está activo para venta",
            example = "true",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private Boolean active = true;
}