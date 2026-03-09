package com.portfolio.inventory_app.mapper;

import com.portfolio.inventory_app.dto.base.UsuarioBase;
import com.portfolio.inventory_app.dto.resources.UsuarioDTO;
import com.portfolio.inventory_app.dto.request.UsuarioRequest;
import com.portfolio.inventory_app.model.entities.Usuario;
import com.portfolio.inventory_app.model.enums.Rol;
import org.springframework.stereotype.Component;

@Component
public class UsuarioMapper {

    /**
     * Mapea datos de un Request (Record) hacia un Builder de la Entidad Usuario.
     */
    @SuppressWarnings("unchecked")
    public <B extends Usuario.UsuarioBuilder<?, ?>> B mapBaseToBuilder(B builder, UsuarioBase request) {
        if (request == null) return builder;
        return (B) builder
                .nombre(request.nombre())
                .dni(request.dni())
                .email(request.email())
                .telefono(request.telefono())
                .domicilio(request.domicilio())
                .estado(request.estado());
    }

    /**
     * Mapea datos de una Entidad hacia un Builder de un Record de Respuesta.
     * IMPORTANTE: Cada mapper de dominio (Empleado/Cliente) llamará a sus propios setters
     * en el builder para evitar errores de casteo entre Records distintos.
     */
    public UsuarioDTO toDTO(Usuario usuario) {
        if (usuario == null) return null;

        return UsuarioDTO.builder()
                .id(usuario.getId())
                .nombre(usuario.getNombre())
                .dni(usuario.getDni())
                .email(usuario.getEmail())
                .telefono(usuario.getTelefono())
                .domicilio(usuario.getDomicilio())
                .rol(usuario.getRol() != null ? usuario.getRol().name() : null)
                .estado(usuario.isEstado())
                .build();
    }

    /**
     * Mapea un Request inicial a Entidad (Útil para el registro básico).
     */
    public Usuario toEntity(UsuarioRequest request) {
        if (request == null) return null;

        return Usuario.builder()
                .nombre(request.nombre())
                .dni(request.dni())
                .email(request.email())
                .telefono(request.telefono())
                .domicilio(request.domicilio())
                .rol(request.rol() != null ? Rol.valueOf(request.rol().toUpperCase()) : null)
                .build();
    }

    /**
     * Actualiza una entidad existente con datos de un Record.
     */
    public void mapBaseDataToEntity(UsuarioBase request, Usuario usuario) {
        if (request == null || usuario == null) return;
        usuario.setNombre(request.nombre());
        usuario.setDni(request.dni());
        usuario.setEmail(request.email());
        usuario.setTelefono(request.telefono());
        usuario.setDomicilio(request.domicilio());
        usuario.setEstado(request.estado());
    }
}