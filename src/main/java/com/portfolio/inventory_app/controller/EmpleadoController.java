package com.portfolio.inventory_app.controller;

import com.portfolio.inventory_app.dto.resources.EmpleadoDTO;
import com.portfolio.inventory_app.service.EmpleadoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/empleados")
@RequiredArgsConstructor
public class EmpleadoController {


    private final EmpleadoService empleadoService;

    @GetMapping
    public ResponseEntity<List<EmpleadoDTO.Response>> listar() {
        return ResponseEntity.ok(empleadoService.listAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmpleadoDTO.Response> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(empleadoService.obtenerEmpleadoDto(id));
    }


    @PostMapping
    public ResponseEntity<EmpleadoDTO.Response> crear(@RequestBody EmpleadoDTO.Request request) {
        return new ResponseEntity<>(empleadoService.registrarEmpleado(request), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmpleadoDTO.Response> actualizar(@PathVariable Long id, @RequestBody EmpleadoDTO.Request request) {
        return ResponseEntity.ok(empleadoService.update(id, request));
    }

    @PatchMapping("/{id}/estado")
    public ResponseEntity<EmpleadoDTO.Response> cambiarEstado(@PathVariable Long id, @RequestParam boolean activo) {
        return ResponseEntity.ok(empleadoService.updateStatus(id, activo));
    }

}
