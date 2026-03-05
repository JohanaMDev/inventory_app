package com.portfolio.inventory_app.model.enums;


import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

public enum Rol {
//    SUPER_ADMIN, // Acceso total, gestión de empleados y sectores
//    ADMIN,       // Gestión operativa (inventario, ventas)
//    SELLER,      // El "Vendedor" de tu lista (puede ver prospectos y ayudar clientes)
//    CLIENT,      // El cliente que se loguea para comprar
//    GUEST;       // Usuario no registrado (solo ve catálogo público)


// Acceso total, gestión de empleados y sectores
    SUPER_ADMIN(Set.of(Permission.values())),

    // Gestión operativa (inventario, ventas, clientes)
    ADMIN(Set.of(
            Permission.CAN_MANAGE_SALES,
            Permission.CAN_MANAGE_INVENTORY,
            Permission.CAN_MANAGE_CLIENTS,
            Permission.CAN_VIEW_SENSITIVE_REPORTS,
            Permission.CAN_MANAGE_EMPLOYEES
    )),

    // Vendedor (puede ver prospectos/clientes y vender)
    SELLER(Set.of(
            Permission.CAN_MANAGE_SALES,
            Permission.CAN_MANAGE_CLIENTS
    )),

    // Cliente que se loguea para comprar
    CLIENT(Set.of(Permission.CAN_MANAGE_SALES)),

    // Usuario no registrado (sin permisos de escritura)
    GUEST(Collections.emptySet());

    private final Set<Permission> permissions;

    Rol(Set<Permission> permissions) {
        this.permissions = permissions;
    }

    public Set<Permission> getPermissions() {
        return permissions;
    }

    // Este método es el que "engaña" a Spring para que vea los permisos como Authorities
    public Set<SimpleGrantedAuthority> getAuthorities() {
        // 1. Convertimos cada Permission en una SimpleGrantedAuthority
        Set<SimpleGrantedAuthority> authorities = getPermissions().stream()
                .map(p -> new SimpleGrantedAuthority(p.name()))
                .collect(Collectors.toSet());

        // 2. Agregamos el Rol mismo como una autoridad con prefijo ROLE_ (Convención de Spring)
        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));

        return authorities;
    }
}
