package com.portfolio.inventory_app.mapper;

import com.portfolio.inventory_app.dto.resources.ClienteDTO;
import com.portfolio.inventory_app.model.entities.Cliente;
import com.portfolio.inventory_app.model.enums.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ClienteMapper {

    private final UsuarioMapper usuarioMapper;
    private final PasswordEncoder passwordEncoder;

    public Cliente toEntity(ClienteDTO.Request request) {
        if (request == null) return null;
        Cliente cliente = Cliente.builder()
                .preferencia(request.preferencia())
                .comportamiento(request.comportamiento() != null ? Comportamiento.valueOf(request.comportamiento().toUpperCase()) : null)
                .origen(request.origen() != null ? Origen.valueOf(request.origen().toUpperCase()) : null)
                .puntuacion(request.puntuacion())
                .categoriaFiscal(request.categoriaFiscal() != null ? CategoriaFiscal.valueOf(request.categoriaFiscal().toUpperCase()) : null)
                .tipoCliente(request.tipoCliente() != null ? TipoCliente.valueOf(request.tipoCliente().toUpperCase()) : null)
                .build();
        if (cliente.getUsuario() != null) {
            usuarioMapper.mapBaseDataToEntity(request, cliente.getUsuario());
            if (request.password() != null && !request.password().isBlank()) {
                cliente.getUsuario().setPassword(passwordEncoder.encode(request.password()));
            }
        }
        return cliente;
    }

    public ClienteDTO.Response toResponseDTO(Cliente cliente) {
        if (cliente == null) return null;
        return ClienteDTO.Response.builder()
                .id(cliente.getId())
                .preferencia(cliente.getPreferencia())
                .comportamiento(cliente.getComportamiento() != null ? cliente.getComportamiento().toString() : null)
                .origen(cliente.getOrigen() != null ? cliente.getOrigen().toString() : null)
                .puntuacion(cliente.getPuntuacion())
                .categoriaFiscal(cliente.getCategoriaFiscal() != null ? cliente.getCategoriaFiscal().toString() : null)
                .tipoCliente(cliente.getTipoCliente() != null ? cliente.getTipoCliente().toString() : null)
                // Campos aplanados de UsuarioBase
                .nombre(cliente.getUsuario().getNombre())
                .dni(cliente.getUsuario().getDni())
                .email(cliente.getUsuario().getEmail())
                .telefono(cliente.getUsuario().getTelefono())
                .domicilio(cliente.getUsuario().getDomicilio())
                .rol(cliente.getUsuario().getRol() != null ? cliente.getUsuario().getRol().name() : null)
                .estado(cliente.getUsuario().isEstado())
                .build();
    }

    public void updateFromDto(ClienteDTO.Request request, Cliente cliente) {
        if (request == null || cliente == null) return;

        if (cliente.getUsuario() != null) {
            usuarioMapper.mapBaseDataToEntity(request, cliente.getUsuario());
            if (request.password() != null && !request.password().isBlank()) {
                cliente.getUsuario().setPassword(passwordEncoder.encode(request.password()));
            }
            if (request.rol() != null) {
                cliente.getUsuario().setRol(Rol.valueOf(request.rol().toUpperCase()));
            }
        }

        if (request.tipoCliente() != null) {
            cliente.setTipoCliente(TipoCliente.valueOf(request.tipoCliente().toUpperCase()));
        }
        if (request.comportamiento() != null) {
            cliente.setComportamiento(Comportamiento.valueOf(request.comportamiento().toUpperCase()));
        }
        if (request.categoriaFiscal() != null) {
            cliente.setCategoriaFiscal(CategoriaFiscal.valueOf(request.categoriaFiscal().toUpperCase()));
        }
        if (request.origen() != null) {
            cliente.setOrigen(Origen.valueOf(request.origen().toUpperCase()));
        }
        cliente.setPuntuacion(request.puntuacion());
        cliente.setPreferencia(request.preferencia());
    }
}

