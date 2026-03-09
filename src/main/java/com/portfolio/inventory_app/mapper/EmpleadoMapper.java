package com.portfolio.inventory_app.mapper;

import com.portfolio.inventory_app.dto.resources.EmpleadoDTO;
import com.portfolio.inventory_app.model.entities.Empleado;
import com.portfolio.inventory_app.model.enums.Disponibilidad;
import com.portfolio.inventory_app.model.enums.Rol;
import com.portfolio.inventory_app.service.PuestoService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmpleadoMapper {

    private final UsuarioMapper usuarioMapper;
    private final PuestoService puestoService;
    private final PasswordEncoder passwordEncoder;

    public Empleado toEntity(EmpleadoDTO.Request request) {
        if (request == null) return null;
        Empleado empleado = Empleado.builder()
                .legajo(request.legajo())
                .disponibilidad(request.disponibilidad() != null ?
                        Disponibilidad.valueOf(request.disponibilidad().toUpperCase()) : null)
                .sucursal(request.sucursal())
                .puesto(request.puestoId() != null ?
                        puestoService.encontrarPorId(request.puestoId()) : null)
                .objetivoMensual(request.objetivoMensual())
                .obraSocial(request.obraSocial())
                .build();

        if (empleado.getUsuario() != null) {
            usuarioMapper.mapBaseDataToEntity(request, empleado.getUsuario());
            if (request.password() != null && !request.password().isBlank()) {
                empleado.getUsuario().setPassword(passwordEncoder.encode(request.password()));
            }
        }
        return empleado;
    }

    public EmpleadoDTO.Response toResponseDTO(Empleado empleado) {
        if (empleado == null) return null;
        return EmpleadoDTO.Response.builder()
                .id(empleado.getId())
                .legajo(empleado.getLegajo())
                .sucursal(empleado.getSucursal())
                .puestoNombre(empleado.getPuesto() != null ? empleado.getPuesto().getNombre() : null)
                .disponibilidad(empleado.getDisponibilidad() != null ? empleado.getDisponibilidad().toString() : null)
                .objetivoMensual(empleado.getObjetivoMensual())
                .nombre(empleado.getUsuario().getNombre())
                .dni(empleado.getUsuario().getDni())
                .email(empleado.getUsuario().getEmail())
                .telefono(empleado.getUsuario().getTelefono())
                .domicilio(empleado.getUsuario().getDomicilio())
                .rol(empleado.getUsuario().getRol() != null ? empleado.getUsuario().getRol().name() : null)
                .estado(empleado.getUsuario().isEstado())
                .build();
    }

    public void updateFromDto(EmpleadoDTO.Request request, Empleado empleado) {
        if (request == null || empleado == null) return;
        if (empleado.getUsuario() != null) {
            usuarioMapper.mapBaseDataToEntity(request, empleado.getUsuario());
            if (request.password() != null && !request.password().isBlank()) {
                empleado.getUsuario().setPassword(passwordEncoder.encode(request.password()));
            }
            if (request.rol() != null) {
                empleado.getUsuario().setRol(Rol.valueOf(request.rol().toUpperCase()));
            }
        }

        empleado.setLegajo(request.legajo());
        empleado.setSucursal(request.sucursal());
        empleado.setObraSocial(request.obraSocial());
        empleado.setObjetivoMensual(request.objetivoMensual());

        if (request.disponibilidad() != null) {
            empleado.setDisponibilidad(Disponibilidad.valueOf(request.disponibilidad().toUpperCase()));
        }
        if (request.puestoId() != null) {
            empleado.setPuesto(puestoService.encontrarPorId(request.puestoId()));
        }
    }
}