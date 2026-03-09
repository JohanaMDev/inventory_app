package com.portfolio.inventory_app.mapper;

import com.portfolio.inventory_app.dto.resources.ProductoDTO;
import com.portfolio.inventory_app.model.entities.CategoriaProductos;
import com.portfolio.inventory_app.model.entities.Producto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductoMapper {

    public Producto toEntity(ProductoDTO.Request request, CategoriaProductos categoria) {
        if (request == null) return null;
        return Producto.builder()
                .nombre(request.nombre())
                .descripcion(request.descripcion())
                .marca(request.marca())
                .codigoBarras(request.codigoBarras())
                .precioVenta(request.precioVenta())
                .precioCosto(request.precioCosto())
                .margenGanancia(request.margenGanancia())
                .iva(request.iva())
                .stockActual(request.stockActual())
                .stockMinimo(request.stockMinimo())
                .categoriaProductos(categoria)
                .activo(true)
                .build();
    }

    public ProductoDTO.Response toResponseDTO(Producto producto) {
        if (producto == null) return null;

        return ProductoDTO.Response.builder()
                .id(producto.getId())
                .nombre(producto.getNombre())
                .descripcion(producto.getDescripcion())
                .marca(producto.getMarca())
                .codigoBarras(producto.getCodigoBarras())
                .precioVenta(producto.getPrecioVenta())
                .stockActual(producto.getStockActual())
                .activo(producto.isActivo())
                .categoriaId(producto.getCategoriaProductos() != null ? producto.getCategoriaProductos().getId() : null)
                .nombreCategoria(producto.getCategoriaProductos() != null ? producto.getCategoriaProductos().getNombre() : "Sin Categoría")
                .bajoStock(producto.getStockMinimo() != null && producto.getStockActual() <= producto.getStockMinimo())
                .build();
    }

    public void updateFromDto(ProductoDTO.Request request, Producto producto, CategoriaProductos categoria) {
        if (request == null || producto == null) return;

        producto.setNombre(request.nombre().toUpperCase().trim());
        producto.setDescripcion(request.descripcion());
        producto.setMarca(request.marca());
        producto.setCodigoBarras(request.codigoBarras());
        producto.setCategoriaProductos(null);

        producto.setPrecioVenta(request.precioVenta());
        producto.setPrecioCosto(request.precioCosto());
        producto.setMargenGanancia(request.precioCosto());
        producto.setIva(request.iva());

        producto.setStockActual(request.stockActual()   );
        producto.setStockMinimo(request.stockMinimo());

    }

}