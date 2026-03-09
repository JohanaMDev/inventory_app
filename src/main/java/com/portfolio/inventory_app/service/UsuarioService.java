package com.portfolio.inventory_app.service;

import com.portfolio.inventory_app.dto.request.UsuarioRequest;
import com.portfolio.inventory_app.dto.resources.UsuarioDTO;
import com.portfolio.inventory_app.exception.EmailInvalidException;
import com.portfolio.inventory_app.exception.UnauthorizedRoleException;
import com.portfolio.inventory_app.mapper.UsuarioMapper;
import com.portfolio.inventory_app.model.entities.Usuario;
import com.portfolio.inventory_app.model.enums.Rol;
import com.portfolio.inventory_app.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UsuarioDTO
    registrarAdministrador(UsuarioRequest request) {
        if (usuarioRepository.findByEmail(request.email()).isPresent()) {
            throw new EmailInvalidException("El email ya existe");
        }
        Rol nuevoRol;
        try {
            nuevoRol = Rol.valueOf(request.rol().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new UnauthorizedRoleException("El rol proporcionado no es válido."); //
        }
        Usuario usuario = usuarioMapper.toEntity(request);
        usuario.setRol(nuevoRol);
        usuario.setPassword(passwordEncoder.encode(request.password()));
        usuario.setEstado(true);
        Usuario usuarioGuardado = usuarioRepository.save(usuario);
        return usuarioMapper.toDTO(usuarioGuardado);
    }


    @Transactional
    public Usuario crearUsuario(UsuarioRequest request) {
        if (usuarioRepository.findByEmail(request.email()).isPresent()) {
            throw new EmailInvalidException("El email " + request.email() + " ya está registrado en el sistema");
        }
        var user = Usuario.builder()
                .nombre(request.nombre())
                .email(request.email())
                .dni(request.dni())
                .domicilio(request.domicilio())
                .telefono(request.telefono())
                .password(passwordEncoder.encode(request.password()))
                .rol(Rol.valueOf(request.rol()))
                .estado(true).build();
        return usuarioRepository.save(user);
    }

    public Usuario buscarPorEmail(String email) {
        return usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + email));
    }

    public UsuarioDTO findByEmail(String email) {
        return usuarioMapper.toDTO(buscarPorEmail(email));
    }

}