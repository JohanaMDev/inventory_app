package com.portfolio.inventory_app.mapper;

import com.portfolio.inventory_app.dto.resources.DetalleVentaDTO;
import com.portfolio.inventory_app.dto.resources.VentaDTO;
import com.portfolio.inventory_app.model.entities.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class VentaMapper {

    public Venta toEntity(VentaDTO.Request request, Cliente cliente, Empleado empleado, List<DetalleVenta> detalles) {
        if (request == null) return null;

        return Venta.builder()
                .cliente(cliente)
                .empleado(empleado)
                .fecha(LocalDateTime.now())
                .detalles(detalles)
                .build();
    }

    public VentaDTO.Response toResponseDTO(Venta venta) {
        if (venta == null) return null;

        return VentaDTO.Response.builder()
                .id(venta.getId())
                .fecha(venta.getFecha())
                .nombreEmpleado(venta.getEmpleado().getNombre())
                .nombreCliente(venta.getCliente().getNombre())
                .total(venta.getTotal())
                .detalles(venta.getDetalles().stream()
                        .map(this::mapItemToResponseDTO)
                        .toList())
                .build();
    }

    private DetalleVentaDTO.Response mapItemToResponseDTO(DetalleVenta detalle) {
        return DetalleVentaDTO.Response.builder()
                .productoId(detalle.getProducto().getId())
                .productoNombre(detalle.getProducto().getNombre())
                .cantidad(detalle.getCantidad())
                .precioUnitario(detalle.getPrecioUnitario())
                .subtotal(detalle.getPrecioUnitario().multiply(new BigDecimal(detalle.getCantidad())))
                .build();
    }

}