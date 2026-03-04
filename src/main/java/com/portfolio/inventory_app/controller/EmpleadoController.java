package com.portfolio.inventory_app.controller;

import com.portfolio.inventory_app.dto.EmpleadoDto;
import com.portfolio.inventory_app.model.entities.Empleado;
import com.portfolio.inventory_app.service.EmpleadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/empleados")
public class EmpleadoController {

    @Autowired
    private EmpleadoService empleadoService;

    @GetMapping
    public List<EmpleadoDto> listarTodos() {

        return empleadoService.listAll();
    }

    @PostMapping
    @PreAuthorize("hasAuthority('CAN_MANAGE_EMPLOYEES')")
    public EmpleadoDto guardar(@RequestBody Empleado empleado) {
        return empleadoService.save(empleado);
    }

    

}
