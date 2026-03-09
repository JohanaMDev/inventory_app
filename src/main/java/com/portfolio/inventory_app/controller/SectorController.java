package com.portfolio.inventory_app.controller;

import com.portfolio.inventory_app.dto.resources.SectorDTO;
import com.portfolio.inventory_app.service.SectorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sectores")
@RequiredArgsConstructor
public class SectorController {

    private final SectorService sectorService;

    @GetMapping
    public ResponseEntity<List<SectorDTO.Response>> listar() {
        return ResponseEntity.ok(sectorService.listAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SectorDTO.Response> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(sectorService.getResponseById(id));
    }

    @PostMapping
    public ResponseEntity<SectorDTO.Response> crear(@RequestBody SectorDTO.Request request) {
        return new ResponseEntity<>(sectorService.save(request), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SectorDTO.Response> actualizar(@PathVariable Long id, @RequestBody SectorDTO.Request request) {
        return ResponseEntity.ok(sectorService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        sectorService.delete(id);
        return ResponseEntity.noContent().build();
    }
}