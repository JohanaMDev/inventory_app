package com.portfolio.inventory_app.mapper;

import com.portfolio.inventory_app.dto.resources.SectorDTO;
import com.portfolio.inventory_app.model.entities.Sector;
import org.springframework.stereotype.Component;

@Component
public class SectorMapper {

    public Sector toEntity(SectorDTO.Request request) {
        if (request == null) return null;
        return Sector.builder()
                .nombre(request.nombre().toUpperCase().trim())
                .build();
    }

    public SectorDTO.Response toResponseDTO(Sector sector) {
        if (sector == null) return null;
        return SectorDTO.Response.builder()
                .id(sector.getId())
                .nombre(sector.getNombre())
                .cantidadPuestos(sector.getPuestos() != null ? sector.getPuestos().size() : 0)
                .build();
    }

    public void updateFromDto(SectorDTO.Request request, Sector sector) {
        if (request == null || sector == null) return;
        sector.setNombre(request.nombre().toUpperCase().trim());
    }

}