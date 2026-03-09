package com.portfolio.inventory_app.controller;


import com.portfolio.inventory_app.dto.resources.ProductoDTO;
import com.portfolio.inventory_app.service.ProductoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/productos")
@RequiredArgsConstructor
public class ProductoController {

    private final ProductoService productoService;

    @GetMapping
    public ResponseEntity<List<ProductoDTO.Response>> listarActivos() {
        return ResponseEntity.ok(productoService.listActivos());
    }

    @GetMapping("/buscar")
    public ResponseEntity<ProductoDTO.Response> buscarProducto(@RequestParam String codigo) {
        try {
            return ResponseEntity.ok(productoService.getResponseByCodigo(codigo));
        } catch (Exception e) {
            return ResponseEntity.ok(productoService.getResponseById(Long.parseLong(codigo)));
        }
    }

    @PostMapping
    @PreAuthorize("hasAnyAuthority('CAN_MANAGE_INVENTORY','CAN_CONFIGURE_SYSTEM')")
    public ResponseEntity<ProductoDTO.Response> guardar(@RequestBody ProductoDTO.Request request) {
        return new ResponseEntity<>(productoService.save(request), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('CAN_MANAGE_INVENTORY','CAN_CONFIGURE_SYSTEM')")
    public ResponseEntity<ProductoDTO.Response> actualizarProducto(@PathVariable Long id, @RequestBody ProductoDTO.Request request) {
        return ResponseEntity.ok(productoService.update(id, request));
    }

    @PatchMapping("/{id}/precioVenta")
    @PreAuthorize("hasAnyAuthority('CAN_MANAGE_INVENTORY','CAN_CONFIGURE_SYSTEM')")
    public ResponseEntity<ProductoDTO.Response> actualizarPrecio(@PathVariable Long id, @RequestParam BigDecimal nuevoPrecio) {
        return ResponseEntity.ok(productoService.actualizarPrecio(id, nuevoPrecio));
    }

    @GetMapping("/alertas-stock")
    public ResponseEntity<List<ProductoDTO.Response>> obtenerAlertas() {
        return ResponseEntity.ok(productoService.obtenerAlertasStock());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('CAN_MANAGE_INVENTORY','CAN_CONFIGURE_SYSTEM')")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        productoService.delete(id);
        return ResponseEntity.noContent().build();
    }

}

