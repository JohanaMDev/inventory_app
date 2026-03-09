package com.portfolio.inventory_app.service;

import com.portfolio.inventory_app.dto.resources.PuestoDTO;
import com.portfolio.inventory_app.exception.BusinessLogicException;
import com.portfolio.inventory_app.mapper.PuestoMapper;
import com.portfolio.inventory_app.model.entities.Puesto;
import com.portfolio.inventory_app.repository.PuestoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PuestoService {

    private final PuestoRepository puestoRepository;
    private final PuestoMapper puestoMapper;

    public List<PuestoDTO.Response> listAll() {
        return puestoRepository.findAll().stream()
                .map(puestoMapper::toResponseDTO)
                .toList();
    }

    public PuestoDTO.Response getResponseById(Long id) {
        return puestoMapper.toResponseDTO(encontrarPorId(id));
    }

    @Transactional
    public PuestoDTO.Response save(PuestoDTO.Request request) {
        Puesto puesto = puestoMapper.toEntity(request);
        return puestoMapper.toResponseDTO(puestoRepository.save(puesto));
    }

    @Transactional
    public PuestoDTO.Response update(Long id, PuestoDTO.Request request) {
        Puesto puesto = encontrarPorId(id);
        puestoMapper.updateFromDto(request, puesto);
        return puestoMapper.toResponseDTO(puestoRepository.save(puesto));
    }

    public Puesto encontrarPorId(Long puestoId) {
        return puestoRepository.findById(puestoId)
                .orElseThrow(() -> new BusinessLogicException("Puesto no encontrado con ID: " + puestoId));
    }
}