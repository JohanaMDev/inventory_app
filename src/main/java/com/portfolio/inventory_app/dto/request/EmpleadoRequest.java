package com.portfolio.inventory_app.dto.request;

import java.math.BigDecimal;
import java.time.LocalDate;

public record EmpleadoRequest (
    String nombre,
    String email,
    String dni,
    String password,
    String domicilio,
    String telefono,
    String legajo,
    Long puestoId,
    Long sectorId,
    LaboralRequest laboral
) {}

    record LaboralRequest(
            String cuit,
            String cbu,
            BigDecimal salarioBase,
            LocalDate fechaIngreso,
            String horarioEntrada,
            String horarioSalida
    ) {}
