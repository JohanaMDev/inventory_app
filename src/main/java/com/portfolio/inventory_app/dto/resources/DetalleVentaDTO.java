package com.portfolio.inventory_app.dto.resources;

import lombok.Builder;

import java.math.BigDecimal;

public class DetalleVentaDTO {

    @Builder
    public record Request(
         Long id,
         Long productoId,
         Integer cantidad
    ){
    }

    @Builder
    public record Response (
        Long id,
        Long productoId,
        String productoNombre,
        Integer cantidad,
        BigDecimal precioUnitario,
        BigDecimal subtotal
    ){
    }

}