package com.portfolio.inventory_app.dto.resources;

import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class VentaDTO {

    @Builder
    public record Request (
        Long empleadoId,
        Long clienteId,
        List<DetalleVentaDTO.Request> detalles
        //private String metodoPago;
    ){}

    @Builder
    public record Response (
        Long id,
        LocalDateTime fecha,
        String nombreEmpleado,
        String nombreCliente,
        List<DetalleVentaDTO.Response> detalles,
        BigDecimal total
    ){}

}
