package com.portfolio.inventory_app.dto.resources;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

public class CategoriaProductosDTO {

    @Builder
    public record Request(
            @NotBlank(message = "El nombre de la categoría es obligatorio")
            String nombre,
            boolean activo
    ) {
    }

    @Builder
    public record Response(
            Long id,
            String nombre,
            Integer cantidadProductos,
            boolean activo
    ) {
    }
}