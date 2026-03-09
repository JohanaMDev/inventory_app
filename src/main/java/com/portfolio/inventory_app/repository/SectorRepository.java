package com.portfolio.inventory_app.repository;

import com.portfolio.inventory_app.model.entities.Sector;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SectorRepository extends JpaRepository<Sector, Long> {

    boolean existsByNombre(String nombreNormalizado);
    Optional<Sector> findByNombreIgnoreCase(String nombre);
    @Query("SELECT s.nombre FROM Sector s")
    List<String> findAllNombres();
}
