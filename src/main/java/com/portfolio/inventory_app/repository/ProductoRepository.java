package com.portfolio.inventory_app.repository;

import com.portfolio.inventory_app.model.entities.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {

    Optional<Producto> findByCodigoBarras(String codigoBarras);
    boolean existsByCodigoBarras(String codigoBarras);

    List<Producto> findByActivoTrue();
    List<Producto> findByActivoFalse();
    List<Producto> findByNombreContainingIgnoreCase(String nombre);
    List<Producto> findByMarcaContainingIgnoreCase(String marca);

    List<Producto> findByCategoriaProductos_Id(Long categoriaId);

    @Query("SELECT p FROM Producto p WHERE p.stockActual <= p.stockMinimo AND p.activo = true")
    List<Producto> findProductosEnAlertaStock();

    List<Producto> findByCategoriaId(Long id);
}
