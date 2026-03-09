package com.portfolio.inventory_app.dto.resources;

import lombok.Builder;

public class SectorDTO {


    @Builder
    public record Request (
        String nombre
    ){}

    @Builder
    public record Response (
        Long id,
        String nombre,
        Integer cantidadPuestos
    ){}

}