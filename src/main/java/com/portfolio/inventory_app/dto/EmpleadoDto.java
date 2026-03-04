package com.portfolio.inventory_app.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class EmpleadoDto {
    
    private Long id;

    private String nombre;
    private String email;
    private String dni;
    private  String rol;
    private String telefono;
    private String domicilio;
    private boolean estado;
    
    private String legajo;
    private String puesto;
    private String disponibilidad;
    private String sucursal;
    private BigDecimal objetivoMensual;
    private String obraSocial;

}
