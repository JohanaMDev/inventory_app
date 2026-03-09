package com.portfolio.inventory_app.model.domain;

import com.portfolio.inventory_app.model.enums.*;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Entity
@Table(name = "informacion_laboral")
public class InformacionLaboral {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (unique = true, nullable = false)
    private String cuit;

    @Column (unique = true, nullable = false)
    private String cbu;

    @Temporal(TemporalType.DATE)
    @Column(name = "fecha_ingreso", nullable = false)
    private Date ingreso;

    @Enumerated(EnumType.STRING)
    @Column(name = "categoria_fiscal")
    private CategoriaFiscal categoriaFiscal;

    @Enumerated(EnumType.STRING)
    private Contrato contrato;

    @Enumerated(EnumType.STRING)
    private Jornada jornada;

    @Enumerated(EnumType.STRING)
    private Modalidad modalidad;

    @Column(name= "salario_base")
    private BigDecimal sueldoBasico = BigDecimal.ZERO;
    @Column(name = "aplica_presentismo")
    private boolean aplicaPresentismo;
    @Column(name = "aplica_sindicato")
    private boolean aplicaSindicato;
    @Column(name = "porc_antig_anio")
    private BigDecimal porcentajeAntiguedadPorAnio = BigDecimal.ZERO;
    private BigDecimal comision = BigDecimal.ZERO;

    @Temporal(TemporalType.DATE)
    @Column(name = "fecha_egreso")
    private Date egreso;

}