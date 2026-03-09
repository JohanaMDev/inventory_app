package com.portfolio.inventory_app.service;

import com.portfolio.inventory_app.dto.resources.SectorDTO;
import com.portfolio.inventory_app.exception.BusinessLogicException;
import com.portfolio.inventory_app.mapper.SectorMapper;
import com.portfolio.inventory_app.model.entities.Sector;
import com.portfolio.inventory_app.repository.SectorRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SectorService {

    private final SectorRepository sectorRepository;
    private final SectorMapper sectorMapper;

    public List<SectorDTO.Response> listAll() {
        return sectorRepository.findAll().stream()
                .map(sectorMapper::toResponseDTO)
                .toList();
    }

    public Sector encontrarPorId(Long id) {
        return sectorRepository.findById(id)
                .orElseThrow(() -> new BusinessLogicException("Sector no encontrado con ID: " + id));
    }

    public SectorDTO.Response getResponseById(Long id) {
        return sectorMapper.toResponseDTO(encontrarPorId(id));
    }

    @Transactional
    @PreAuthorize("hasAuthority('CAN_CONFIGURE_SYSTEM') or hasAnyRole('SUPER_ADMIN','ADMIN')")
    public SectorDTO.Response save(SectorDTO.Request request) {
        String nombreNormalizado = request.nombre().toUpperCase().trim();
        if (sectorRepository.existsByNombre(nombreNormalizado)) {
            throw new BusinessLogicException("Ya existe un sector con el nombre: " + nombreNormalizado);
        }
        Sector sector = sectorMapper.toEntity(request);
        return sectorMapper.toResponseDTO(sectorRepository.save(sector));
    }

    @Transactional
    @PreAuthorize("hasAuthority('CAN_CONFIGURE_SYSTEM') or hasAnyRole('SUPER_ADMIN','ADMIN')")
    public SectorDTO.Response update(Long id, SectorDTO.Request request) {
        Sector sector = encontrarPorId(id);
        String nuevoNombre = request.nombre().toUpperCase().trim();
        if (!sector.getNombre().equals(nuevoNombre) && sectorRepository.existsByNombre(nuevoNombre)) {
            throw new BusinessLogicException("Ya existe otro sector con el nombre: " + nuevoNombre);
        }
        sectorMapper.updateFromDto(request, sector);
        return sectorMapper.toResponseDTO(sectorRepository.save(sector));
    }

    @Transactional
    @PreAuthorize("hasAuthority('CAN_CONFIGURE_SYSTEM') or hasAnyRole('SUPER_ADMIN','ADMIN')")
    public void delete(Long id) {
        Sector sector = encontrarPorId(id);
        if (sector.getPuestos() != null && !sector.getPuestos().isEmpty()) {
            throw new BusinessLogicException("No se puede eliminar un sector que tiene puestos asociados.");
        }
        sectorRepository.delete(sector);
    }

}