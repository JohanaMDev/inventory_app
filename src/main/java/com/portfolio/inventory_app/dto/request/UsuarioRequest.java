package com.portfolio.inventory_app.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UsuarioRequest (
        @NotBlank(message = "El nombre es obligatorio")
        String nombre,

        String domicilio,

        String telefono,

        @NotBlank(message = "El DNI es obligatorio")
        @Size(min = 7, max = 8, message = "El DNI debe tener entre 7 y 8 caracteres")
        String dni,

        @Email(message = "El formato del email no es válido")
        @NotBlank(message = "El email es obligatorio")
        String email,

        @NotBlank(message = "La contraseña es obligatoria")
        @Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres")
        String password,

        @NotBlank(message = "El rol es obligatorio")
        String rol
) {}