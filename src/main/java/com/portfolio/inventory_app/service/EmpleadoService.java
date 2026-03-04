package com.portfolio.inventory_app.service;

import com.portfolio.inventory_app.dto.EmpleadoDto;
import com.portfolio.inventory_app.exception.BusinessLogicException;
import com.portfolio.inventory_app.exception.EmployeeStatusException;
import com.portfolio.inventory_app.model.entities.Empleado;
import com.portfolio.inventory_app.model.enums.Disponibilidad;
import com.portfolio.inventory_app.repository.EmpleadoRepository;
import com.portfolio.inventory_app.util.DataValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmpleadoService {

    @Autowired private EmpleadoRepository empleadoRepository;
    @Autowired private DataValidator validator;

    public List<EmpleadoDto> listAll() {
        return empleadoRepository.findAll()
        .stream()
        .map(this::entityToDto)
        .collect(java.util.stream.Collectors.toList());
    }

    public EmpleadoDto save(Empleado empleado) {
        empleadoRepository.findByDni(empleado.getDni()).ifPresent(e -> {
            throw new BusinessLogicException("El empleado ya existe");
        });
        validator.validarEmail(empleado.getEmail());
        
        empleado.setPassword(new BCryptPasswordEncoder().encode(empleado.getPassword()));
        if (empleado.getLegajo() == null || empleado.getLegajo().isEmpty()) {
            String suffix = empleado.getDni().length() > 4
                    ? empleado.getDni().substring(empleado.getDni().length() - 4)
                    : empleado.getDni();
            empleado.setLegajo("EMP-" + suffix);
    }
        Empleado saved = empleadoRepository.save(empleado);
        return entityToDto(saved);
    }

    private EmpleadoDto entityToDto(Empleado empleado) {
    EmpleadoDto dto = new EmpleadoDto();
    dto.setId(empleado.getId());
    dto.setNombre(empleado.getNombre());
    dto.setEmail(empleado.getEmail());
    dto.setDni(empleado.getDni());
    dto.setRol(empleado.getRol().name());
    dto.setTelefono(empleado.getTelefono());
    dto.setDomicilio(empleado.getDomicilio());
    dto.setEstado(empleado.isEstado());

    dto.setLegajo(empleado.getLegajo());
    dto.setDisponibilidad(empleado.getDisponibilidad().name());
    dto.setSucursal(empleado.getSucursal());
    dto.setObjetivoMensual(empleado.getObjetivoMensual());
    dto.setObraSocial(empleado.getObraSocial());

        if (empleado.getPuesto() != null) {
            dto.setPuesto(empleado.getPuesto().getNombre());
        } else {
            dto.setPuesto("SIN ASIGNAR");
        }
    
    return dto;
    }

    public Empleado encontrarPorId(Long id) {
        return empleadoRepository.findById(id)
                .orElseThrow(() -> new BusinessLogicException("Empleado no encontrado con ID: " + id));
    }

    public EmpleadoDto obtenerEmpleadoDto(Long id) {
        Empleado e = encontrarPorId(id);
        return entityToDto(e);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public EmpleadoDto updateStatus(Long id, boolean nuevoEstado) {
        Empleado existing = empleadoRepository.findById(id)
                .orElseThrow(() -> new BusinessLogicException("Empleado no encontrado con ID: " + id));
        existing.setEstado(nuevoEstado);
        Empleado actualizado = empleadoRepository.save(existing);
        return entityToDto(actualizado);
    }

    private void verificarEstadoIntegral(Empleado empleado) {
        if (!empleado.isEstado()) throw new RuntimeException("Empleado dado de baja.");
        if (empleado.getDisponibilidad() != Disponibilidad.PRESENTE) {
            throw new EmployeeStatusException("Situación administrativa: " + empleado.getDisponibilidad());
        }
        // verificarHorarioLaboral(empleado);
    }

}
