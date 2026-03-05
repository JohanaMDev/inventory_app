package com.portfolio.inventory_app.model.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.portfolio.inventory_app.model.domain.InformacionLaboral;
import com.portfolio.inventory_app.model.enums.*;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "empleados")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Empleado extends Usuario {

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

}
