package com.portfolio.inventory_app.controller;

import com.portfolio.inventory_app.dto.resources.CategoriaProductosDTO;
import com.portfolio.inventory_app.service.CategoriaProductosService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categorias")
@RequiredArgsConstructor
public class CategoriaController {

    private final CategoriaProductosService categoriaService;

    @GetMapping
    public ResponseEntity<List<CategoriaProductosDTO.Response>> listarActivos() {
        return ResponseEntity.ok(categoriaService.listarActivas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoriaProductosDTO.Response> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(categoriaService.buscarCategoriaPorId(id));
    }

    @GetMapping("/buscar")
    public ResponseEntity<CategoriaProductosDTO.Response> buscarPorNombre(@RequestParam String nombre) {
        return ResponseEntity.ok(categoriaService.buscarCategoriaPorNombre(nombre));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN') or hasAuthority('CAN_MANAGE_INVENTORY')")
    public ResponseEntity<CategoriaProductosDTO.Response> crear(@RequestBody @Valid CategoriaProductosDTO.Request request) {
        return new ResponseEntity<>(categoriaService.guardar(request), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN') or hasAuthority('CAN_MANAGE_INVENTORY')")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        categoriaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}