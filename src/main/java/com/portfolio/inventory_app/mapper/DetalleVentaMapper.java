package com.portfolio.inventory_app.mapper;

import com.portfolio.inventory_app.dto.resources.DetalleVentaDTO;
import com.portfolio.inventory_app.model.entities.DetalleVenta;
import com.portfolio.inventory_app.model.entities.Producto;
import com.portfolio.inventory_app.service.ProductoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class DetalleVentaMapper {

    private final ProductoService productoService;

    public DetalleVenta toEntity(DetalleVentaDTO.Request request) {
        if (request == null) return null;
        Producto producto = productoService.getById(request.id());
        return DetalleVenta.builder()
                .producto(producto)
                .cantidad(request.cantidad())
                .precioUnitario(producto.getPrecioVenta())
                .build();
    }

    public DetalleVentaDTO.Response toResponseDTO(DetalleVenta detalle) {
        if (detalle == null) return null;
        BigDecimal subtotal = detalle.getPrecioUnitario()
                .multiply(new BigDecimal(detalle.getCantidad()));

        return DetalleVentaDTO.Response.builder()
                .id(detalle.getId())
                .productoId(detalle.getProducto().getId())
                .productoNombre(detalle.getProducto().getNombre())
                .cantidad(detalle.getCantidad())
                .precioUnitario(detalle.getPrecioUnitario())
                .subtotal(subtotal)
                .build();
    }

}