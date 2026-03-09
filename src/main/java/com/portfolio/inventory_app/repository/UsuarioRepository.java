package com.portfolio.inventory_app.repository;

import com.portfolio.inventory_app.model.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findById(Long id);
    Optional<Usuario> findByEmail(String email);
    @Query("SELECT u FROM Usuario u WHERE TYPE(u) = Empleado")
    List<Usuario> findAllEmpleados();
    @Query("SELECT u FROM Usuario u WHERE TYPE(u) = Cliente")
    List<Usuario> findAllClientes();
    Optional<Usuario> findByDni(String dni);
    Optional<Usuario> findByRol(String rol);
}
