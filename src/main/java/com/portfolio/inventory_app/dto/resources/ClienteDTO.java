package com.portfolio.inventory_app.dto.resources;

import com.portfolio.inventory_app.dto.base.UsuarioBase;
import lombok.Builder;

public class ClienteDTO {

    @Builder
    public record Request (
            String nombre, String dni, String telefono, String domicilio,
            String email, String password, String rol, boolean estado,
            String tipoCliente, String comportamiento, String categoriaFiscal,
            String origen, Integer puntuacion, String preferencia
    ) implements UsuarioBase {}

    @Builder
    public record Response (
            Long id,
            String nombre, String dni, String telefono, String domicilio,
            String email, String rol, boolean estado,
            String tipoCliente, String comportamiento, String categoriaFiscal,
            String origen, Integer puntuacion, String preferencia
    ) implements UsuarioBase {}
}