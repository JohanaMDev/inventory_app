package com.portfolio.inventory_app.service;

import com.portfolio.inventory_app.model.entities.Puesto;
import com.portfolio.inventory_app.repository.PuestoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PuestoService {

    @Autowired private PuestoRepository puestoRepository;

    public List<Puesto> listAll() {
        return puestoRepository.findAll();
    }



}
