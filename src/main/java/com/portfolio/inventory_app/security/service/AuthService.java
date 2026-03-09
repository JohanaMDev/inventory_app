package com.portfolio.inventory_app.security.service;

import com.portfolio.inventory_app.dto.request.LoginRequest;
import com.portfolio.inventory_app.dto.request.UsuarioRequest;
import com.portfolio.inventory_app.dto.response.AuthResponseDTO;
import com.portfolio.inventory_app.exception.EmailInvalidException;
import com.portfolio.inventory_app.model.entities.Token;
import com.portfolio.inventory_app.model.entities.Usuario;
import com.portfolio.inventory_app.model.enums.TokenType;
import com.portfolio.inventory_app.repository.TokenRepository;
import com.portfolio.inventory_app.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UsuarioService usuarioService;
    private final JwtService jwtService;
    private final TokenRepository tokenRepository;
    private final AuthenticationManager authenticationManager;

    public AuthResponseDTO register(@Valid UsuarioRequest request) {
        var userDTO = usuarioService.registrarAdministrador(request);
        var user = usuarioService.buscarPorEmail(userDTO.email());
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        saveUserToken(user, jwtToken);
        return new AuthResponseDTO(jwtToken, refreshToken);
    }

    public AuthResponseDTO login(@Valid LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password()
                )
        );

        var user = usuarioService.buscarPorEmail(request.email());
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);

        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);

        return new AuthResponseDTO(jwtToken, refreshToken);
    }

    public AuthResponseDTO refreshToken(final String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new IllegalArgumentException("Cabecera de autorización inválida");
        }

        final String refreshToken = authHeader.substring(7);
        final String userEmail = jwtService.extractUsername(refreshToken);

        if (userEmail == null) {
            throw new EmailInvalidException("Token de refresco inválido");
        }

        final Usuario user = usuarioService.buscarPorEmail(userEmail);

        if (!jwtService.isTokenValid(refreshToken, user)) {
            throw new IllegalArgumentException("Token de refresco expirado o inválido");
        }

        final String accessToken = jwtService.generateToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, accessToken);

        return new AuthResponseDTO(accessToken, refreshToken);
    }

    private void saveUserToken(Usuario user, String jwtToken) {
        var token = Token.builder()
                .usuario(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(final Usuario user) {
        final List<Token> validUserTokens = tokenRepository
                .findAllValidTokensByUser(user.getId());
        if (!validUserTokens.isEmpty()) {
            validUserTokens.forEach(token -> {
                token.setExpired(true);
                token.setRevoked(true);
            });
            tokenRepository.saveAll(validUserTokens);
        }
    }
}