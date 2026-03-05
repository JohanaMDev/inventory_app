package com.portfolio.inventory_app.controller;

import com.portfolio.inventory_app.model.entities.Venta;
import com.portfolio.inventory_app.service.VentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/ventas")
public class VentaController {

    @Autowired
    private VentaService ventaService;

    @GetMapping
    public ResponseEntity<List<Venta>> getAll() {
        List<Venta> ventas = ventaService.listAll();
        return ResponseEntity.ok(ventas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Venta> getById(@PathVariable Long id) {
        Venta v = ventaService.findById(id);
        return (v != null) ? ResponseEntity.ok(v) : ResponseEntity.notFound().build();
    }

    @PostMapping
    @PreAuthorize("hasAuthority('CAN_MANAGE_SALES')")
    public ResponseEntity<?> create(@RequestBody Venta venta) {
        try {
            Venta nuevaVenta = ventaService.registrarVenta(venta);
            return new ResponseEntity<>(nuevaVenta, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/empleado/{empleadoId}")
    public ResponseEntity<List<Venta>> getByEmpleado(@PathVariable Long empleadoId) {
        List<Venta> ventas = ventaService.findByEmpleadoId(empleadoId);
        return ventas.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(ventas);
    }

    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<Venta>> getByCliente(@PathVariable Long clienteId) {
        List<Venta> ventas = ventaService.findByClienteId(clienteId);
        return ventas.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(ventas);
    }

    @GetMapping("/fechas")
    public ResponseEntity<List<Venta>> getByFechas(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fin) {
        List<Venta> ventas = ventaService.findByRangoFechas(inicio, fin);
        return ResponseEntity.ok(ventas);
    }
}
