package com.portfolio.inventory_app.repository;


import com.portfolio.inventory_app.model.entities.Empleado;
import com.portfolio.inventory_app.model.enums.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmpleadoRepository extends JpaRepository<Empleado, Long> {

    List<Empleado> findByRol(Rol rol);
    Optional<Empleado> findByDni(String Dni);
    List<Empleado> findByNombreIgnoreCase(String nombre);
    List<Empleado> findByPuestoSector(String sector);
    List<Empleado> findByPuestoNombre(String puesto);
    Optional<Empleado> findByLegajo(String legajo);

}
