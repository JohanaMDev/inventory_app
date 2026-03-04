package com.portfolio.inventory_app.model.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name= "categorias")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoriaProductos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Debe ingresar una Categoria")
    @Column(unique = true)
    private String nombre;

    private boolean activo = true;

    public CategoriaProductos(String nombre, Boolean activo) {
        this.nombre = nombre;
        this.activo = activo;
    }
}
