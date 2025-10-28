package com.example.transitweb.controller;

import com.example.transitweb.entity.MantenimientoBus;
import com.example.transitweb.service.MantenimientoBusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mantenimiento")
public class MantenimientoBusController {

    @Autowired
    private MantenimientoBusService mantenimientoBusService;

    @PostMapping
    public ResponseEntity<MantenimientoBus> crear(@RequestParam Long busId, @RequestParam String descripcion) {
        return new ResponseEntity<>(mantenimientoBusService.crear(busId, descripcion), HttpStatus.CREATED);
    }

    @GetMapping("/bus/{busId}")
    public ResponseEntity<List<MantenimientoBus>> listarPorBus(@PathVariable Long busId) {
        return ResponseEntity.ok(mantenimientoBusService.listarPorBus(busId));
    }
}


