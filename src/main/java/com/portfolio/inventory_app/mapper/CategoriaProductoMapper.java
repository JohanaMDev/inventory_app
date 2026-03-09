package com.portfolio.inventory_app.mapper;

import com.portfolio.inventory_app.dto.resources.CategoriaProductosDTO;
import com.portfolio.inventory_app.model.entities.CategoriaProductos;
import org.springframework.stereotype.Component;

@Component
public class CategoriaProductoMapper {

    public CategoriaProductos toEntity(CategoriaProductosDTO.Request request) {
        if (request == null) return null;

        return CategoriaProductos.builder()
                .nombre(request.nombre().trim().toUpperCase())
                .activo(request.activo())
                .build();
    }

    public CategoriaProductosDTO.Response toResponseDTO(CategoriaProductos categoria) {
        if (categoria == null) return null;

        return CategoriaProductosDTO.Response.builder()
                .id(categoria.getId())
                .nombre(categoria.getNombre())
                .activo(categoria.isActivo())
                .build();
    }

    public void updateFromDto(CategoriaProductosDTO.Request request, CategoriaProductos categoria) {
        if (request == null || categoria == null) return;
        categoria.setNombre(request.nombre().trim().toUpperCase());
        categoria.setActivo(request.activo());
    }
}
