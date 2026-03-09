package com.portfolio.inventory_app.model.entities;

import jakarta.persistence.*;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "productos")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(columnDefinition = "TEXT")
    private String descripcion;

    @Builder.Default
    private boolean activo= true;

    @Column(nullable = false)
    private BigDecimal precioVenta;

    @Column (name = "precio_costo")
    private BigDecimal precioCosto;

    @Column(name = "margen_ganancia")
    private BigDecimal margenGanancia;

    @Builder.Default
    private BigDecimal iva = new BigDecimal("21.0");

    @Builder.Default
    @Column(nullable = false)
    private Integer stockActual = 0;

    private Integer stockMinimo;

    @Column(name = "codigo_barras", unique = true, nullable = false)
    private String codigoBarras;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoria_id")
    private CategoriaProductos categoriaProductos;

    private String marca;

}
