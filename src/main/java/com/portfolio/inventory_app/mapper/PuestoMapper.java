package com.portfolio.inventory_app.mapper;

import com.portfolio.inventory_app.dto.resources.PuestoDTO;
import com.portfolio.inventory_app.model.entities.Puesto;
import com.portfolio.inventory_app.service.SectorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PuestoMapper {

    private final SectorService sectorService;

    public Puesto toEntity(PuestoDTO.Request request) {
        if (request == null) return null;

        return Puesto.builder()
                .nombre(request.nombre().toUpperCase().trim())
                .sector(request.sectorId() != null ? sectorService.encontrarPorId(request.sectorId()) : null)
                .build();
    }

    public PuestoDTO.Response toResponseDTO(Puesto puesto) {
        if (puesto == null) return null;
        return PuestoDTO.Response.builder()
                .id(puesto.getId())
                .nombre(puesto.getNombre())
                .nombreSector(puesto.getSector() != null ? puesto.getSector().getNombre() : "GENERAL")
                .cantidadEmpleados(puesto.getEmpleados() != null ? puesto.getEmpleados().size() : 0)
                .build();
    }

    public void updateFromDto(PuestoDTO.Request request, Puesto puesto) {
        if (request == null || puesto == null) return;
        puesto.setNombre(request.nombre().toUpperCase().trim());
        if (request.sectorId() != null) {
            puesto.setSector(sectorService.encontrarPorId(request.sectorId()));
        }
    }

}
