package com.portfolio.inventory_app.dto.resources;

import com.portfolio.inventory_app.dto.base.UsuarioBase;
import lombok.Builder;

import java.math.BigDecimal;

public class EmpleadoDTO {

    @Builder
    public record Request (
            String nombre, String dni, String telefono, String domicilio,
            String email, String password, String rol, boolean estado,
            String legajo, String disponibilidad, String sucursal,
            BigDecimal objetivoMensual, String obraSocial, Long puestoId
    ) implements UsuarioBase {}

    @Builder
    public record Response (
            Long id,
            String nombre, String dni, String telefono, String domicilio,
            String email, String rol, boolean estado,
            String legajo, String sucursal, String puestoNombre,
            String disponibilidad, BigDecimal objetivoMensual
    ) implements UsuarioBase{}

}