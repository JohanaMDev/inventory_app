package com.portfolio.inventory_app.service;

import com.portfolio.inventory_app.dto.resources.ClienteDTO;
import com.portfolio.inventory_app.exception.BusinessLogicException;
import com.portfolio.inventory_app.mapper.ClienteMapper;
import com.portfolio.inventory_app.model.entities.Cliente;
import com.portfolio.inventory_app.model.enums.CategoriaFiscal;
import com.portfolio.inventory_app.model.enums.Comportamiento;
import com.portfolio.inventory_app.repository.ClienteRepository;
import com.portfolio.inventory_app.util.DataValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final ClienteMapper clienteMapper;
    private final DataValidator validator;

    public List<ClienteDTO.Response> listAll() {
        return clienteRepository.findAll().stream()
                .map(clienteMapper::toResponseDTO)
                .toList();
    }

    public ClienteDTO.Response obtenerPorId(Long id) {
        return clienteMapper.toResponseDTO(getById(id));
    }

    public Cliente getById(Long id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new BusinessLogicException("Error: Cliente con ID " + id + " no encontrado."));
    }

    public Cliente validarClienteParaVenta(Long id) {
        if (id == null || id == 1L) {
            return getById(1L);
        }
        Cliente cliente = getById(id);
        if (cliente.getComportamiento() == Comportamiento.MOROSO ||
                cliente.getComportamiento() == Comportamiento.FRAUDULENTO) {
            throw new BusinessLogicException("Operación bloqueada. Historial: " + cliente.getComportamiento());
        }
        verificarEstadoIntegral(cliente);
        return cliente;
    }

    public ClienteDTO.Response validarClienteParaVentaResponse(Long id) {
        Cliente clienteValido = validarClienteParaVenta(id);
        return clienteMapper.toResponseDTO(clienteValido);
    }

    @Transactional
    public ClienteDTO.Response save(ClienteDTO.Request request) {
        clienteRepository.findByDni(request.dni()).ifPresent(c -> {
            throw new BusinessLogicException("El documento " + request.dni() + " ya está registrado.");
        });
        validator.validarEmail(request.email());
        Cliente cliente = clienteMapper.toEntity(request);
        validarDocumentacionSegunFiscalidad(cliente);
        return clienteMapper.toResponseDTO(clienteRepository.save(cliente));
    }

    @Transactional
    public ClienteDTO.Response update(Long id, ClienteDTO.Request request) {
        Cliente existente = getById(id);
        if (!existente.getEmail().equals(request.email())) {
            validator.validarEmail(request.email());
        }
        clienteMapper.updateFromDto(request, existente);
        validarDocumentacionSegunFiscalidad(existente);
        return clienteMapper.toResponseDTO(clienteRepository.save(existente));
    }

    private void verificarEstadoIntegral(Cliente cliente) {
        if (!cliente.isEstado()) {
            throw new BusinessLogicException("El cliente " + cliente.getNombre() + " está inactivo.");
        }
    }

    private void validarDocumentacionSegunFiscalidad(Cliente cliente) {
        if (cliente.getCategoriaFiscal() == CategoriaFiscal.RESPONSABLE_INSCRIPTO) {
            if (cliente.getDni() == null || cliente.getDni().length() != 11) {
                throw new BusinessLogicException("Para Responsable Inscripto se requiere CUIT de 11 dígitos.");
            }
        }
    }
}