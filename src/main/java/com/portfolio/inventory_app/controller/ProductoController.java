package com.portfolio.inventory_app.controller;


import com.portfolio.inventory_app.model.entities.Producto;
import com.portfolio.inventory_app.service.ProductoService;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @GetMapping
    public ResponseEntity<List<Producto>> listarActivos() {

        return ResponseEntity.ok(productoService.listActivos());
    }

    @GetMapping("/buscar")
    public ResponseEntity<Producto> buscarProducto(@RequestParam String codigo) {
        try {
            return ResponseEntity.ok(productoService.buscarPorCodigoBarras(codigo));
        } catch (Exception e) {
            return ResponseEntity.ok(productoService.getById(Long.parseLong(codigo)));
        }
    }

    @PostMapping
    @PreAuthorize("hasAuthority('CAN_MANAGE_INVENTORY)")
    public ResponseEntity<Producto> guardar(@RequestBody Producto producto) {
        return new ResponseEntity<>(productoService.save(producto), HttpStatus.CREATED);
    }

    @PatchMapping("/{id}/precio")
    @PreAuthorize("hasAuthority('CAN_MANAGE_INVENTORY)")
    public ResponseEntity<Void> actualizarPrecio(@PathVariable Long id, @RequestParam BigDecimal nuevoPrecio) {
        productoService.actualizarPrecio(id, nuevoPrecio);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/alertas-stock")
    public ResponseEntity<List<Producto>> obtenerAlertas() {
        return ResponseEntity.ok(productoService.obtenerAlertasStock());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('CAN_MANAGE_INVENTORY)")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        productoService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('CAN_MANAGE_INVENTORY)")
    public ResponseEntity<?> actualizarProducto(@PathVariable Long id, @RequestBody Producto producto) {
        return new ResponseEntity<>( productoService.update(id, producto), HttpStatus.OK);
    }

}
