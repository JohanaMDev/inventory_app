package com.portfolio.inventory_app.dto.resources;

import lombok.Builder;

public class PuestoDTO {


    @Builder
    public record Request (
        String nombre,
        Long sectorId
    ){}

    @Builder
    public record Response (
        Long id,
        String nombre,
        String nombreSector,
        Integer cantidadEmpleados
    ){}
}