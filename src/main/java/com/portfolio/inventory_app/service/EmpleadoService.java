package com.portfolio.inventory_app.service;

import com.portfolio.inventory_app.dto.resources.EmpleadoDTO;
import com.portfolio.inventory_app.exception.BusinessLogicException;
import com.portfolio.inventory_app.exception.EmployeeStatusException;
import com.portfolio.inventory_app.mapper.EmpleadoMapper;
import com.portfolio.inventory_app.model.entities.Empleado;
import com.portfolio.inventory_app.model.enums.Disponibilidad;
import com.portfolio.inventory_app.model.enums.Rol;
import com.portfolio.inventory_app.repository.EmpleadoRepository;
import com.portfolio.inventory_app.util.DataValidator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmpleadoService {

    private final EmpleadoRepository empleadoRepository;
    private final EmpleadoMapper empleadoMapper;
    private final DataValidator validator;
    private final PasswordEncoder passwordEncoder;

    public List<EmpleadoDTO.Response> listAll() {
        return empleadoRepository.findAll().stream()
                .map(empleadoMapper::toResponseDTO)
                .toList();
    }

    @PreAuthorize("hasAuthority('CAN_MANAGE_EMPLOYEES') or hasAnyRole('SUPER_ADMIN','ADMIN')")
    @Transactional
    public EmpleadoDTO.Response registrarEmpleado(EmpleadoDTO.Request request) {
        empleadoRepository.findByLegajo(request.legajo()).ifPresent(e -> {
            throw new BusinessLogicException("El Legajo: " + request.legajo() + " ya existe en el sistema");
        });
        validator.validarEmail(request.email());
        Empleado empleado = empleadoMapper.toEntity(request);
            empleado.getUsuario().setPassword(passwordEncoder.encode(request.password()));
            empleado.getUsuario().setRol(Rol.EMPLOYEE);
            empleado.getUsuario().setEstado(true);
            Empleado empleadoGuardado = empleadoRepository.save(empleado);
            return empleadoMapper.toResponseDTO(empleadoGuardado);
    }

    public Empleado encontrarPorId(Long id) {
        return empleadoRepository.findById(id)
                .orElseThrow(() -> new BusinessLogicException("Empleado no encontrado con ID: " + id));
    }

    public EmpleadoDTO.Response obtenerEmpleadoDto(Long id) {
        return empleadoMapper.toResponseDTO(encontrarPorId(id));
    }

    @PreAuthorize("hasAuthority('CAN_MANAGE_EMPLOYEES') or hasAnyRole('SUPER_ADMIN','ADMIN')")
    @Transactional
    public EmpleadoDTO.Response updateStatus(Long id, boolean nuevoEstado) {
        Empleado existing = encontrarPorId(id);
        existing.setEstado(nuevoEstado);
        return empleadoMapper.toResponseDTO(empleadoRepository.save(existing));
    }

    @Transactional
    public EmpleadoDTO.Response update(Long id, EmpleadoDTO.Request request) {
        Empleado existing = encontrarPorId(id);
        if (!existing.getEmail().equals(request.email())) {
            validator.validarEmail(request.email());
        }
        empleadoMapper.updateFromDto(request, existing);
        return empleadoMapper.toResponseDTO(empleadoRepository.save(existing));
    }

    private void verificarEstadoIntegral(Empleado empleado) {
        if (!empleado.isEstado()) throw new BusinessLogicException("Empleado dado de baja.");
        if (empleado.getDisponibilidad() != Disponibilidad.PRESENTE) {
            throw new EmployeeStatusException("Situación administrativa: " + empleado.getDisponibilidad());
        }
    }

}
