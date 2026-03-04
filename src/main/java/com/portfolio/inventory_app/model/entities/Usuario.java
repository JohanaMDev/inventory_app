package com.portfolio.inventory_app.model.entities;

import com.portfolio.inventory_app.model.enums.Rol;
import com.portfolio.inventory_app.security.Token;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "usuarios")
@Inheritance(strategy = InheritanceType.JOINED)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Usuario implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column (nullable = false, length = 20)
    private Rol rol;

    @Column( nullable = false, length = 100)
    private String nombre;

    private String domicilio;

    private String telefono;

    @Column (unique = true, nullable = false)
    private String dni;

    @Column (unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Builder.Default
    @Column(name = "estado")
    private boolean estado= true;

    @Builder.Default
    @OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY)
    private List<Token> tokens = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.rol.name()));

        if (this instanceof Empleado emp && emp.getPuesto() != null) {
            Puesto p = emp.getPuesto();
            if(p.isPuedeConfigurarSistema()) authorities.add(new SimpleGrantedAuthority("CAN_CONFIGURE_SYSTEM"));
            if(p.isPuedeGestionarInventario()) authorities.add(new SimpleGrantedAuthority("CAN_MANAGE_INVENTORY"));
            if(p.isPuedeVender()) authorities.add(new SimpleGrantedAuthority("CAN_MANAGE_SALES"));
            if(p.isPuedeGestionarEmpleados()) authorities.add(new SimpleGrantedAuthority("CAN_MANAGE_EMPLOYEES"));  
            if(p.isPuedeGestionarClientes()) authorities.add(new SimpleGrantedAuthority("CAN_MANAGE_CUSTOMERS"));   
            if(p.isPuedeVerReportesSensibles()) authorities.add(new SimpleGrantedAuthority("CAN_VIEW_SENSITIVE_REPORTS"));  
            if(p.isPuedeRealizarMantenimiento()) authorities.add(new SimpleGrantedAuthority("CAN_PERFORM_MAINTENANCE"));    
        }

        return authorities;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}
