package com.portfolio.inventory_app.dto.resources;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

import java.math.BigDecimal;

public class ProductoDTO {

    @Builder
    public record Request(
            @NotBlank(message = "El nombre es obligatorio")
            String nombre,
            String descripcion,
            String marca,
            String codigoBarras,
            @DecimalMin(value = "0.0", inclusive = false)
            BigDecimal precioVenta,
            BigDecimal precioCosto,
            BigDecimal margenGanancia,
            BigDecimal iva,
            @Min(0)
            Integer stockActual,
            Integer stockMinimo,
            Long categoriaId
    ) {
    }

    @Builder
    public record Response (
        Long id,
        String nombre,
        String descripcion,
        String marca,
        String codigoBarras,
        BigDecimal precioVenta,
        Integer stockActual,
        boolean activo,
        Long categoriaId,
        String nombreCategoria,

        boolean bajoStock
    ){}

}
