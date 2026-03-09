package com.portfolio.inventory_app.model.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.portfolio.inventory_app.model.domain.InformacionLaboral;
import com.portfolio.inventory_app.model.enums.*;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@Entity
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table(name = "empleados")
public class Empleado extends Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "puesto_id")
    @JsonIgnoreProperties("empleados")
    private Puesto puesto;

    @Column(unique = true)
    private String legajo;

    @Enumerated(EnumType.STRING)
    private Disponibilidad disponibilidad;

    private String sucursal;

    @OneToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "informacion_laboral_id", nullable = false)
    private InformacionLaboral informacionLaboral;

    @Column(name = "objetivo_mensual")
    private BigDecimal objetivoMensual;

    @Column(name = "obra_social")
    private String obraSocial;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "usuario_id", referencedColumnName = "id", unique = true, nullable = false)
    private Usuario usuario;

}