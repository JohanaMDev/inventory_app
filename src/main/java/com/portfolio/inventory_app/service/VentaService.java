package com.portfolio.inventory_app.service;

import com.portfolio.inventory_app.dto.resources.DetalleVentaDTO;
import com.portfolio.inventory_app.dto.resources.VentaDTO;
import com.portfolio.inventory_app.mapper.VentaMapper;
import com.portfolio.inventory_app.model.entities.*;
import com.portfolio.inventory_app.repository.VentaRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VentaService {

    private final VentaRepository ventaRepository;
    private final VentaMapper ventaMapper;
    private final ClienteService clienteService;
    private final EmpleadoService empleadoService;
    private final DetalleVentaService detalleVentaService;

    public List<VentaDTO.Response> listAll() {
        return ventaRepository.findAll().stream()
                .map(ventaMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public VentaDTO.Response findById(Long id) {
        Venta venta = ventaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Venta no encontrada con ID: " + id));
        return ventaMapper.toResponseDTO(venta);
    }

    @PreAuthorize("hasAuthority('CAN_MANAGE_SALES') or hasAnyRole('SUPER_ADMIN','ADMIN','SELLER')")
    @Transactional
    public VentaDTO.Response registrarVenta(VentaDTO.Request request) {
        Cliente cliente = clienteService.validarClienteParaVenta(request.clienteId());
        Empleado empleado = empleadoService.encontrarPorId(request.empleadoId());

        Venta venta = Venta.builder()
                .cliente(cliente)
                .empleado(empleado)
                .fecha(LocalDateTime.now())
                .build();
        List<DetalleVenta> detalles = new ArrayList<>();
        BigDecimal total = BigDecimal.ZERO;
        for (DetalleVentaDTO.Request itemDto : request.detalles()) {
            DetalleVenta detalle = detalleVentaService.procesarLinea(itemDto, venta);
            detalles.add(detalle);
            BigDecimal subtotal = detalle.getPrecioUnitario().multiply(new BigDecimal(detalle.getCantidad()));
            total = total.add(subtotal);
        }
        venta.setDetalles(detalles);
        venta.setTotal(total);
        return ventaMapper.toResponseDTO(ventaRepository.save(venta));
    }

    public List<VentaDTO.Response> findByEmpleadoId(Long empleadoId) {
        return ventaRepository.findByEmpleado_Id(empleadoId).stream()
                        .map(ventaMapper::toResponseDTO).collect(Collectors.toList());
    }

    public List<VentaDTO.Response> findByClienteId(Long clienteId) {
        return ventaRepository.findByCliente_Id(clienteId).stream()
                .map(ventaMapper::toResponseDTO).collect(Collectors.toList());
    }

    public List<VentaDTO.Response> findByRangoFechas(LocalDateTime inicio, LocalDateTime fin) {
        return ventaRepository.findByFechaBetween(inicio, fin).stream()
                .map(ventaMapper::toResponseDTO).collect(Collectors.toList());
    }

}
