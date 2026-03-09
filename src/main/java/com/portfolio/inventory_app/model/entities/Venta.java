package com.portfolio.inventory_app.model.entities;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name ="ventas")
public class Venta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime fecha = LocalDateTime.now();

    @Builder.Default
    @Column(nullable = false)
    private BigDecimal total = BigDecimal.ZERO;

    @OneToMany(mappedBy = "venta", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<DetalleVenta> detalles = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name ="cliente_id", nullable = false)
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name ="empleado_id", nullable = false)
    private Empleado empleado;

    @PrePersist
    @PreUpdate
    public void validarYCalcularTotal() {
        if (this.detalles == null || this.detalles.isEmpty()) {
            this.total = BigDecimal.ZERO;
            return;
        }
        this.total = detalles.stream()
                .map(detalle -> {
                    BigDecimal precio = (detalle.getPrecioUnitario() != null) ?
                            detalle.getPrecioUnitario() : BigDecimal.ZERO;
                    BigDecimal cant = (detalle.getCantidad() != null) ?
                            BigDecimal.valueOf(detalle.getCantidad()) : BigDecimal.ZERO;

                    return precio.multiply(cant);
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .setScale(2, RoundingMode.HALF_UP);

        System.out.println("⚡ JPA Lifecycle: Total calculado automáticamente: " + this.total);
    }
}