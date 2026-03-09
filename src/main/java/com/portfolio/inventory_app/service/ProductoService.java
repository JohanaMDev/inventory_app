package com.portfolio.inventory_app.service;

import com.portfolio.inventory_app.dto.resources.ProductoDTO;
import com.portfolio.inventory_app.exception.BusinessLogicException;
import com.portfolio.inventory_app.exception.StockInsuficienteException;
import com.portfolio.inventory_app.mapper.ProductoMapper;
import com.portfolio.inventory_app.model.entities.CategoriaProductos;
import com.portfolio.inventory_app.model.entities.Producto;
import com.portfolio.inventory_app.repository.ProductoRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductoService {


    private final ProductoRepository productoRepository;
    private final ProductoMapper productoMapper;
    private final CategoriaProductosService categoriaService;

    public List<ProductoDTO.Response> listActivos() {
        return productoRepository.findAll().stream()
                .filter(Producto::isActivo)
                .map(productoMapper::toResponseDTO)
                .toList();
    }

    public List<ProductoDTO.Response> listInactivos() {
        return productoRepository.findAll().stream()
                .filter(p -> !p.isActivo())
                .map(productoMapper::toResponseDTO)
                .toList();
    }

    public List<ProductoDTO.Response> listAll() {
        return productoRepository.findAll().stream()
                .map(productoMapper::toResponseDTO)
                .toList();
    }

    public ProductoDTO.Response getResponseById(Long id) {
        return productoMapper.toResponseDTO(getById(id));
    }

    public Producto getById(Long id) {
        return productoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Producto no encontrado con ID: " + id));
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN','ADMIN')")
    @Transactional
    public ProductoDTO.Response save(ProductoDTO.Request request) {
        CategoriaProductos categoria = (request.categoriaId() != null)
            ? categoriaService.getByIdEntity(request.categoriaId())
                : null;
        Producto p = productoMapper.toEntity(request, categoria);
        calcularPrecioVenta(p);
        return productoMapper.toResponseDTO(productoRepository.save(p));
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN','ADMIN')")
    @Transactional
    public ProductoDTO.Response update(Long id, ProductoDTO.Request request) {
        Producto existente = productoRepository.findById(id)
                .orElseThrow(() -> new BusinessLogicException("Producto no encontrado"));
        CategoriaProductos categoria = null;
        if (request.categoriaId() != null) {
            categoria = categoriaService.getByIdEntity(request.categoriaId());
        }
        productoMapper.updateFromDto(request, existente, categoria);
        return productoMapper.toResponseDTO(productoRepository.save(existente));
    }

    private BigDecimal calcularPrecioVenta(Producto p) {
        if (p.getPrecioVenta() != null && p.getPrecioVenta().compareTo(BigDecimal.ZERO) > 0) {
            if (p.getPrecioCosto() != null && p.getPrecioVenta().compareTo(p.getPrecioCosto()) < 0) {
                throw new BusinessLogicException("El precio de venta no puede ser menor al costo.");
            }
            return p.getPrecioVenta().setScale(2, RoundingMode.HALF_UP);
        }
        BigDecimal costo = (p.getPrecioCosto() != null) ? p.getPrecioCosto() : BigDecimal.ZERO;
        BigDecimal margen = (p.getMargenGanancia() != null) ? p.getMargenGanancia() : BigDecimal.ZERO;
        BigDecimal iva = (p.getIva() != null) ? p.getIva() : BigDecimal.valueOf(21);

        BigDecimal cien = BigDecimal.valueOf(100);
        BigDecimal multiplicadorMargen = margen.divide(cien, 10, RoundingMode.HALF_UP).add(BigDecimal.ONE);
        BigDecimal multiplicadorIva = iva.divide(cien, 10, RoundingMode.HALF_UP).add(BigDecimal.ONE);

        return costo.multiply(multiplicadorMargen)
                .multiply(multiplicadorIva)
                .setScale(2, RoundingMode.HALF_UP);
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN','ADMIN')")
    @Transactional
    public void delete(Long id) {
        Producto p = getById(id);
        p.setActivo(false);
        productoRepository.save(p);
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN','ADMIN')")
    @Transactional
    public ProductoDTO.Response actualizarStock(Long id, Integer cantidad, boolean esIncremento) {
        Producto p = getById(id);
        int nuevoStock = esIncremento ? p.getStockActual() + cantidad : p.getStockActual() - cantidad;
        if (nuevoStock < 0) {
            throw new StockInsuficienteException("Stock insuficiente para: " + p.getNombre());
        }
        p.setStockActual(nuevoStock);
        return productoMapper.toResponseDTO(productoRepository.save(p));
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN','ADMIN')")
    @Transactional
    public ProductoDTO.Response actualizarPrecio(Long id, BigDecimal nuevoPrecio) {
        if (nuevoPrecio.compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessLogicException("El precio debe ser mayor a cero.");
        }
        Producto p = getById(id);
        p.setPrecioVenta(nuevoPrecio);
        return productoMapper.toResponseDTO(productoRepository.save(p));
    }

    public List<ProductoDTO.Response> obtenerAlertasStock() {
        return productoRepository.findAll().stream()
                .filter(p -> p.isActivo() && p.getStockActual() <= p.getStockMinimo())
                .map(productoMapper::toResponseDTO) // Transformamos a DTO
                .toList();
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN','ADMIN')")
    @Transactional
    public ProductoDTO.Response actualizarPrecioPorCosto(Long id, BigDecimal nuevoCosto, BigDecimal nuevoMargen, BigDecimal ivaPorcentaje) {
        Producto p = getById(id);
        p.setPrecioCosto(nuevoCosto);
        if (nuevoMargen != null) p.setMargenGanancia(nuevoMargen);
        if (ivaPorcentaje != null) p.setIva(ivaPorcentaje);

        p.setPrecioVenta(calcularPrecioVenta(p));
        return productoMapper.toResponseDTO(productoRepository.save(p));
    }

    public ProductoDTO.Response getResponseByCodigo(String codigo) {
        return null;
    }
}

