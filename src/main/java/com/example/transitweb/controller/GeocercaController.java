package com.example.transitweb.controller;

import com.example.transitweb.entity.Geocerca;
import com.example.transitweb.repository.GeocercaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/geocercas")
public class GeocercaController {

    @Autowired
    private GeocercaRepository geocercaRepository;

    @GetMapping
    public ResponseEntity<List<Geocerca>> listar() { return ResponseEntity.ok(geocercaRepository.findAll()); }

    @PostMapping
    public ResponseEntity<Geocerca> crear(@RequestBody Geocerca g) { return new ResponseEntity<>(geocercaRepository.save(g), HttpStatus.CREATED); }

    @PutMapping("/{id}")
    public ResponseEntity<Geocerca> actualizar(@PathVariable Long id, @RequestBody Geocerca body) {
        Geocerca g = geocercaRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Geocerca no encontrada"));
        g.setNombre(body.getNombre());
        g.setLatitudCentro(body.getLatitudCentro());
        g.setLongitudCentro(body.getLongitudCentro());
        g.setRadioMetros(body.getRadioMetros());
        g.setZonaCritica(body.getZonaCritica());
        return ResponseEntity.ok(geocercaRepository.save(g));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        geocercaRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}


