package com.portfolio.inventory_app.service;

import com.portfolio.inventory_app.dto.resources.DetalleVentaDTO;
import com.portfolio.inventory_app.mapper.DetalleVentaMapper;
import com.portfolio.inventory_app.model.entities.DetalleVenta;
import com.portfolio.inventory_app.model.entities.Producto;
import com.portfolio.inventory_app.model.entities.Venta;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DetalleVentaService {

    private final ProductoService productoService;
    private final DetalleVentaMapper detalleVentaMapper;

    public DetalleVenta procesarLinea(DetalleVentaDTO.Request request, Venta venta) {
        Producto p = productoService.getById(request.productoId());
        DetalleVenta detalle = DetalleVenta.builder()
                .venta(venta)
                .producto(p)
                .cantidad(request.cantidad())
                .precioUnitario(p.getPrecioVenta())
                .build();
        productoService.actualizarStock(p.getId(), request.cantidad(), false);
        return detalle;
    }

    public List<DetalleVentaDTO.Response> mapListToResponse(List<DetalleVenta> detalles) {
        return detalles.stream()
                .map(detalleVentaMapper::toResponseDTO)
                .toList();
    }

}