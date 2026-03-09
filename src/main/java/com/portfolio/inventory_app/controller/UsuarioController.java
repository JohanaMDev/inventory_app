package com.portfolio.inventory_app.controller;

import com.portfolio.inventory_app.dto.request.UsuarioRequest;
import com.portfolio.inventory_app.dto.resources.UsuarioDTO;
import com.portfolio.inventory_app.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    @PostMapping("/register-admin")
    //@PreAuthorize("hasRole('SUPER_ADMIN')")
    public ResponseEntity<UsuarioDTO> registrarAdmin(@Valid @RequestBody UsuarioRequest request) {
        UsuarioDTO reponse = usuarioService.registrarAdministrador(request);
        return new ResponseEntity<>(reponse, HttpStatus.CREATED);
    }

}
