package com.example.transitweb.controller;

import com.example.transitweb.dto.ReporteEventoRequestDTO;
import com.example.transitweb.entity.ReporteEvento;
import com.example.transitweb.service.ReporteEventoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reportes")
public class ReporteEventoController {

    @Autowired
    private ReporteEventoService reporteEventoService;

    @PostMapping
    public ResponseEntity<ReporteEvento> crear(@RequestBody ReporteEventoRequestDTO request) {
        ReporteEvento r = reporteEventoService.crearReporte(request);
        return new ResponseEntity<>(r, HttpStatus.CREATED);
    }

    @GetMapping("/abiertos")
    public ResponseEntity<List<ReporteEvento>> abiertos() {
        return ResponseEntity.ok(reporteEventoService.getReportesAbiertos());
    }

    @PutMapping("/{id}/estado")
    public ResponseEntity<ReporteEvento> actualizarEstado(@PathVariable Long id, @RequestParam String estado) {
        return ResponseEntity.ok(reporteEventoService.actualizarEstadoReporte(id, estado));
    }
}


