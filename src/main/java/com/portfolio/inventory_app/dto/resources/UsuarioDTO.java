package com.portfolio.inventory_app.dto.resources;

import com.portfolio.inventory_app.dto.base.UsuarioBase;
import lombok.Builder;

@Builder
public record UsuarioDTO(
        Long id,
        String nombre,
        String dni,
        String telefono,
        String domicilio,
        String email,
        String rol,
        boolean estado
) implements UsuarioBase {}