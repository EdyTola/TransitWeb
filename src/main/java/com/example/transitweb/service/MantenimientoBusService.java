package com.example.transitweb.service;

import com.example.transitweb.entity.Bus;
import com.example.transitweb.entity.MantenimientoBus;
import com.example.transitweb.repository.BusRepository;
import com.example.transitweb.repository.MantenimientoBusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class MantenimientoBusService {

    @Autowired
    private MantenimientoBusRepository mantenimientoBusRepository;

    @Autowired
    private BusRepository busRepository;

    @Transactional
    public MantenimientoBus crear(Long busId, String descripcion) {
        Bus bus = busRepository.findById(busId)
                .orElseThrow(() -> new NoSuchElementException("Bus no encontrado"));
        MantenimientoBus m = new MantenimientoBus();
        m.setBus(bus);
        m.setDescripcion(descripcion);
        return mantenimientoBusRepository.save(m);
    }

    @Transactional(readOnly = true)
    public List<MantenimientoBus> listarPorBus(Long busId) {
        Bus bus = busRepository.findById(busId)
                .orElseThrow(() -> new NoSuchElementException("Bus no encontrado"));
        return mantenimientoBusRepository.findAll().stream().filter(x -> x.getBus().getId().equals(bus.getId())).toList();
    }
}


