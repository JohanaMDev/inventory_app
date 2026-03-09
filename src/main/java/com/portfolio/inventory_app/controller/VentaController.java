package com.portfolio.inventory_app.controller;

import com.portfolio.inventory_app.dto.resources.VentaDTO;
import com.portfolio.inventory_app.service.VentaService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/ventas")
public class VentaController {

    {/* PENDIENTE MODIFICACION: Agregar v1 por buena práctica de versionado @RequestMapping("/api/v1/ventas")*/}

    private final VentaService ventaService;

    @GetMapping
    public ResponseEntity<List<VentaDTO.Response>> getAll() {
        return ResponseEntity.ok(ventaService.listAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<VentaDTO.Response> getById(@PathVariable Long id) {
        return ResponseEntity.ok(ventaService.findById(id));
    }

    @PostMapping
    public ResponseEntity<VentaDTO.Response> create(@RequestBody VentaDTO.Request request) {
        return new ResponseEntity<>(ventaService.registrarVenta(request), HttpStatus.CREATED);
    }

    @GetMapping("/empleado/{empleadoId}")
    public ResponseEntity<List<VentaDTO.Response>> getByEmpleado(@PathVariable Long empleadoId) {
        List<VentaDTO.Response> ventas = ventaService.findByEmpleadoId(empleadoId);
        return ventas.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(ventas);
    }

    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<VentaDTO.Response>> getByCliente(@PathVariable Long clienteId) {
        List<VentaDTO.Response> ventas = ventaService.findByClienteId(clienteId);
        return ventas.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(ventas);
    }

    @GetMapping("/fechas")
    public ResponseEntity<List<VentaDTO.Response>> getByFechas(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime inicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fin) {
        return ResponseEntity.ok(ventaService.findByRangoFechas(inicio, fin));
    }
}