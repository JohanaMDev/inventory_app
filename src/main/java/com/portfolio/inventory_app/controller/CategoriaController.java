package com.portfolio.inventory_app.controller;

import com.portfolio.inventory_app.model.entities.CategoriaProductos;
import com.portfolio.inventory_app.service.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/categorias")
public class CategoriaController {

    @Autowired private CategoriaService categoriaService;

    @GetMapping
    public ResponseEntity<List<CategoriaProductos>> listarActivos(){
        return ResponseEntity.ok(categoriaService.findByActivoTrue());
    }

    @GetMapping("/buscar")
    public ResponseEntity<CategoriaProductos> buscarCategoria(@RequestParam String nombre) {
        try {
            return ResponseEntity.ok(categoriaService.buscarCategoriaPorNombre(nombre));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<CategoriaProductos> create(@RequestBody CategoriaProductos categoria){
        return new ResponseEntity<>(categoriaService.save(categoria), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        try {
            categoriaService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
