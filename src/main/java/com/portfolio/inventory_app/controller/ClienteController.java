package com.portfolio.inventory_app.controller;

import com.portfolio.inventory_app.dto.resources.ClienteDTO;
import com.portfolio.inventory_app.service.ClienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteService clienteService;

    @GetMapping
    public ResponseEntity<List<ClienteDTO.Response>> listarTodos() {
        return ResponseEntity.ok(clienteService.listAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteDTO.Response> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(clienteService.obtenerPorId(id));
    }

    @PostMapping
    public ResponseEntity<ClienteDTO.Response> crear(@RequestBody ClienteDTO.Request request) {
        return new ResponseEntity<>(clienteService.save(request), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClienteDTO.Response> actualizar(@PathVariable Long id, @RequestBody ClienteDTO.Request request) {
        return ResponseEntity.ok(clienteService.update(id, request));
    }

    @GetMapping("/{id}/validar-venta")
    public ResponseEntity<ClienteDTO.Response> validarClienteParaVentaResponse(@PathVariable Long id) {
        return ResponseEntity.ok(clienteService.validarClienteParaVentaResponse(id));
    }
}