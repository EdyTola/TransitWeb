package com.example.transitweb.controller;

import com.example.transitweb.entity.Paradero;
import com.example.transitweb.entity.Ruta;
import com.example.transitweb.repository.ParaderoRepository;
import com.example.transitweb.repository.RutaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/rutas")
public class RutaController {

    @Autowired
    private RutaRepository rutaRepository;

    @Autowired
    private ParaderoRepository paraderoRepository;

    @GetMapping
    public ResponseEntity<List<Ruta>> listar() { return ResponseEntity.ok(rutaRepository.findAll()); }

    @PostMapping
    public ResponseEntity<Ruta> crear(@RequestBody Ruta r) { return new ResponseEntity<>(rutaRepository.save(r), HttpStatus.CREATED); }

    @PutMapping("/{id}")
    public ResponseEntity<Ruta> actualizar(@PathVariable Long id, @RequestBody Ruta body) {
        Ruta r = rutaRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Ruta no encontrada"));
        r.setNombre(body.getNombre());
        r.setDescripcion(body.getDescripcion());
        return ResponseEntity.ok(rutaRepository.save(r));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        rutaRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // --- Paraderos ---
    @GetMapping("/{rutaId}/paraderos")
    public ResponseEntity<List<Paradero>> paraderos(@PathVariable Long rutaId) {
        Ruta r = rutaRepository.findById(rutaId).orElseThrow(() -> new NoSuchElementException("Ruta no encontrada"));
        return ResponseEntity.ok(paraderoRepository.findAll().stream().filter(p -> p.getRuta().getId().equals(r.getId())).toList());
    }

    @PostMapping("/{rutaId}/paraderos")
    public ResponseEntity<Paradero> crearParadero(@PathVariable Long rutaId, @RequestBody Paradero p) {
        Ruta r = rutaRepository.findById(rutaId).orElseThrow(() -> new NoSuchElementException("Ruta no encontrada"));
        p.setRuta(r);
        return new ResponseEntity<>(paraderoRepository.save(p), HttpStatus.CREATED);
    }
}


