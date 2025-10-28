package com.example.transitweb.controller;

import com.example.transitweb.dto.ReporteEventoRequestDTO;
import com.example.transitweb.entity.ReporteEvento;
import com.example.transitweb.service.ReporteEventoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/panico")
public class PanicoController {

    @Autowired
    private ReporteEventoService reporteEventoService;

    // Botón de pánico con doble confirmación (el frontend gestionará confirmación)
    @PostMapping("/activar")
    public ResponseEntity<ReporteEvento> activar(@RequestBody ReporteEventoRequestDTO request) {
        // Forzar tipo EMERGENCIA
        request.setTipo("EMERGENCIA");
        ReporteEvento r = reporteEventoService.crearReporte(request);
        return new ResponseEntity<>(r, HttpStatus.CREATED);
    }
}


