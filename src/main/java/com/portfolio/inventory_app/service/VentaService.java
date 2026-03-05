package com.portfolio.inventory_app.service;

import com.portfolio.inventory_app.exception.BusinessLogicException;
import com.portfolio.inventory_app.model.entities.*;
import com.portfolio.inventory_app.repository.VentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class VentaService {

    @Autowired private VentaRepository ventaRepository;
    @Autowired private EmpleadoService empleadoService;
    @Autowired private ClienteService clienteService;
    @Autowired private DetalleVentaService detalleVentaService;

    public List<Venta> listAll() {
        return ventaRepository.findAll();
    }

    public Venta findById(Long id) {
        return ventaRepository.findById(id).get();
    }

    @PreAuthorize("hasAuthority('CAN_MANAGE_SALES') or hasAnyRole('SUPER_ADMIN','ADMIN','SELLER')")
    @Transactional
    public Venta registrarVenta(Venta venta) {
        Empleado vendedor = empleadoService.encontrarPorId(venta.getEmpleado().getId());
        Cliente cliente = clienteService.validarClienteParaVenta(venta.getCliente().getId());
        venta.setEmpleado(vendedor);
        venta.setCliente(cliente);
        venta.setFecha(LocalDateTime.now());
        if (venta.getDetalles() == null || venta.getDetalles().isEmpty()) {
            throw new BusinessLogicException("Error: Una venta debe tener al menos un producto.");
        }
        BigDecimal totalAcumulado = BigDecimal.ZERO;
        for (DetalleVenta detalle : venta.getDetalles()) {
            BigDecimal subtotal = detalleVentaService.procesarLinea(detalle, venta);
            totalAcumulado = totalAcumulado.add(subtotal);
        }
        venta.setTotal(totalAcumulado);
        return ventaRepository.save(venta);
    }

    public List<Venta> findByEmpleadoId(Long empleadoId) {
        return ventaRepository.findByEmpleado_Id(empleadoId);
    }

    public List<Venta> findByClienteId(Long clienteId) {
        return ventaRepository.findByCliente_Id(clienteId);
    }

    public List<Venta> findByRangoFechas(LocalDateTime inicio, LocalDateTime fin) {
        return ventaRepository.findByFechaBetween(inicio, fin);
    }
}
