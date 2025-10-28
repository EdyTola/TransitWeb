package com.example.transitweb.controller;

import com.example.transitweb.entity.Bus;
import com.example.transitweb.dto.TelemetriaMessageDTO;
import com.example.transitweb.entity.Telemetria;
import com.example.transitweb.repository.BusRepository;
import com.example.transitweb.repository.TelemetriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/telemetria")
public class TelemetriaController {

    @Autowired
    private TelemetriaRepository telemetriaRepository;

    @Autowired
    private BusRepository busRepository;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    // Envío de posición GPS simulada (con ocupación)
    @PostMapping("/bus/{busId}")
    public ResponseEntity<Telemetria> enviar(@PathVariable Long busId,
                                             @RequestParam double lat,
                                             @RequestParam double lng,
                                             @RequestParam double vel,
                                             @RequestParam(required = false) Integer ocupacion) {
        Bus bus = busRepository.findById(busId).orElseThrow(() -> new NoSuchElementException("Bus no encontrado"));
        Telemetria t = new Telemetria();
        t.setBus(bus);
        t.setLatitud(lat);
        t.setLongitud(lng);
        t.setVelocidad(vel);
        if (ocupacion != null) t.setPasajerosActuales(ocupacion);
        t.setTimestampRegistro(LocalDateTime.now());
        Telemetria saved = telemetriaRepository.save(t);

        TelemetriaMessageDTO msg = new TelemetriaMessageDTO();
        msg.setBusId(busId);
        msg.setLat(lat);
        msg.setLng(lng);
        msg.setVel(vel);
        msg.setOcupacion(saved.getPasajerosActuales());
        messagingTemplate.convertAndSend("/topic/bus/" + busId, msg);

        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    // Contador +/- ocupación
    @PostMapping("/bus/{busId}/ocupacion")
    public ResponseEntity<Telemetria> actualizarOcupacion(@PathVariable Long busId,
                                                          @RequestParam int delta) {
        Bus bus = busRepository.findById(busId).orElseThrow(() -> new NoSuchElementException("Bus no encontrado"));
        Telemetria last = telemetriaRepository.findTopByBusOrderByTimestampRegistroDesc(bus).orElse(null);
        Telemetria nueva = new Telemetria();
        nueva.setBus(bus);
        if (last != null) {
            nueva.setLatitud(last.getLatitud());
            nueva.setLongitud(last.getLongitud());
            nueva.setVelocidad(last.getVelocidad());
            int ocup = (last.getPasajerosActuales() == null ? 0 : last.getPasajerosActuales()) + delta;
            if (ocup < 0) ocup = 0;
            nueva.setPasajerosActuales(ocup);
        } else {
            nueva.setLatitud(0.0);
            nueva.setLongitud(0.0);
            nueva.setVelocidad(0.0);
            nueva.setPasajerosActuales(Math.max(0, delta));
        }
        nueva.setTimestampRegistro(LocalDateTime.now());
        Telemetria saved = telemetriaRepository.save(nueva);

        TelemetriaMessageDTO msg = new TelemetriaMessageDTO();
        msg.setBusId(busId);
        msg.setLat(saved.getLatitud());
        msg.setLng(saved.getLongitud());
        msg.setVel(saved.getVelocidad());
        msg.setOcupacion(saved.getPasajerosActuales());
        messagingTemplate.convertAndSend("/topic/bus/" + busId, msg);

        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @GetMapping("/bus/{busId}/ultima")
    public ResponseEntity<Telemetria> ultima(@PathVariable Long busId) {
        Bus bus = busRepository.findById(busId).orElseThrow(() -> new NoSuchElementException("Bus no encontrado"));
        return telemetriaRepository.findTopByBusOrderByTimestampRegistroDesc(bus)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}


