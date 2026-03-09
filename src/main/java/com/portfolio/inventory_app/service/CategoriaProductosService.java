package com.portfolio.inventory_app.service;

import com.portfolio.inventory_app.dto.resources.CategoriaProductosDTO;
import com.portfolio.inventory_app.exception.BusinessLogicException;
import com.portfolio.inventory_app.exception.ResourceNotFoundException;
import com.portfolio.inventory_app.mapper.CategoriaProductoMapper;
import com.portfolio.inventory_app.model.entities.CategoriaProductos;
import com.portfolio.inventory_app.model.entities.Producto;
import com.portfolio.inventory_app.repository.CategoriaRepository;
import com.portfolio.inventory_app.repository.ProductoRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoriaProductosService {

    private final CategoriaRepository categoriaRepository;
    private final CategoriaProductoMapper categoriaMapper;
    private final ProductoRepository productoRepository;

    @Transactional
    public List<CategoriaProductosDTO.Response> listarTodas() {
        return categoriaRepository.findAll().stream()
                .map(categoriaMapper::toResponseDTO)
                .toList();
    }

    @Transactional
    public CategoriaProductosDTO.Response guardar(CategoriaProductosDTO.Request request) {
        if (categoriaRepository.existsByNombre(request.nombre().toUpperCase())) {
            throw new BusinessLogicException("La categoría " + request.nombre() + " ya existe.");
        }
        CategoriaProductos nueva = categoriaMapper.toEntity(request);
        return categoriaMapper.toResponseDTO(categoriaRepository.save(nueva));
    }

    @Transactional
    public CategoriaProductosDTO.Response actualizar(Long id, CategoriaProductosDTO.Request request) {
        CategoriaProductos existente = categoriaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoría no encontrada"));
        String nuevoNombre = request.nombre().toUpperCase().trim();
        if (!existente.getNombre().equalsIgnoreCase(nuevoNombre) &&
                categoriaRepository.existsByNombre(nuevoNombre)) {
            throw new BusinessLogicException("Ya existe una categoría con el nombre: " + nuevoNombre);
        }
        existente.setNombre(nuevoNombre);
        existente.setActivo(request.activo());
        categoriaMapper.updateFromDto(request, existente);
        return categoriaMapper.toResponseDTO(categoriaRepository.save(existente));
    }

    // Uso interno para Productos
    public CategoriaProductos getByIdEntity(Long id) {
        return categoriaRepository.findById(id)
                .orElseThrow(() -> new BusinessLogicException("Categoría no encontrada con ID: " + id));
    }

    // Uso para el Controller/DTO
    public CategoriaProductosDTO.Response buscarCategoriaPorId(Long id) {
        return categoriaMapper.toResponseDTO(getByIdEntity(id));
    }

    public CategoriaProductosDTO.Response buscarCategoriaPorNombre(String nombre) {
        CategoriaProductos categoria = categoriaRepository.findByNombreIgnoreCase(nombre)
                .orElseThrow(() -> new BusinessLogicException("No se encontró la categoría: " + nombre));
        return categoriaMapper.toResponseDTO(categoria);
    }

    public List<CategoriaProductosDTO.Response> listarActivas() {
        return categoriaRepository.findAll().stream()
                .filter(CategoriaProductos::isActivo)
                .map(categoriaMapper::toResponseDTO)
                .toList();
    }

    @Transactional
    public void delete(Long id) {
        CategoriaProductos categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Categoría no encontrada"));
        List<Producto> productos = productoRepository.findByCategoriaId(id);

        if (!productos.isEmpty()) {
            productos.forEach(p -> p.setCategoriaProductos(null));
            productoRepository.saveAll(productos);
        }
        categoriaRepository.delete(categoria);
    }

}