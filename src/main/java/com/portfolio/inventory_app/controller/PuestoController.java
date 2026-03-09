package com.portfolio.inventory_app.controller;

import com.portfolio.inventory_app.dto.resources.PuestoDTO;
import com.portfolio.inventory_app.service.PuestoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/puestos")
@RequiredArgsConstructor
public class PuestoController {

    private final PuestoService puestoService;

    @GetMapping
    public ResponseEntity<List<PuestoDTO.Response>> listarTodos() {
        return ResponseEntity.ok(puestoService.listAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PuestoDTO.Response> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(puestoService.getResponseById(id));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('CAN_CONFIGURE_SYSTEM') or hasAnyRole('SUPER_ADMIN','ADMIN')")
    public ResponseEntity<PuestoDTO.Response> crear(@RequestBody PuestoDTO.Request request) {
        return new ResponseEntity<>(puestoService.save(request), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('CAN_CONFIGURE_SYSTEM') or hasAnyRole('SUPER_ADMIN','ADMIN')")
    public ResponseEntity<PuestoDTO.Response> actualizar(@PathVariable Long id, @RequestBody PuestoDTO.Request request) {
        return ResponseEntity.ok(puestoService.update(id, request));
    }
}
