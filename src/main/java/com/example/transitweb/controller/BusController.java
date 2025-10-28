package com.example.transitweb.controller;

import com.example.transitweb.entity.Bus;
import com.example.transitweb.repository.BusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/buses")
public class BusController {

    @Autowired
    private BusRepository busRepository;

    @GetMapping
    public ResponseEntity<List<Bus>> listar() { return ResponseEntity.ok(busRepository.findAll()); }

    @PostMapping
    public ResponseEntity<Bus> crear(@RequestBody Bus bus) { return new ResponseEntity<>(busRepository.save(bus), HttpStatus.CREATED); }

    @PutMapping("/{id}")
    public ResponseEntity<Bus> actualizar(@PathVariable Long id, @RequestBody Bus body) {
        Bus b = busRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Bus no encontrado"));
        b.setPlaca(body.getPlaca());
        b.setEstado(body.getEstado());
        b.setRutaActual(body.getRutaActual());
        b.setTieneRampa(body.getTieneRampa());
        return ResponseEntity.ok(busRepository.save(b));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        busRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}


