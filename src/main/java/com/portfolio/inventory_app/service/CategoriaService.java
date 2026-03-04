package com.portfolio.inventory_app.service;

import com.portfolio.inventory_app.model.entities.CategoriaProductos;
import com.portfolio.inventory_app.repository.CategoriaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaService {

    @Autowired private CategoriaRepository categoriaRepository;

    public List<CategoriaProductos> findByActivoTrue() {
        return categoriaRepository.findByActivoTrue();
    }

    public CategoriaProductos buscarCategoriaPorNombre(String nombre) {
        return categoriaRepository.findByNombre(nombre)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada: " + nombre));
    }

    @Transactional
    public CategoriaProductos save(CategoriaProductos categoria) {
        if (categoria.getNombre() == null || categoria.getNombre().isBlank()) {
            throw new RuntimeException("El nombre de la categoría es obligatorio");
        }
        return categoriaRepository.save(categoria);
    }

    @Transactional
    public void delete(Long id) {
        CategoriaProductos categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));
        categoria.setActivo(false);

    }
}
