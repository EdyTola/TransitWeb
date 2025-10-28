package com.example.transitweb.service;

import com.example.transitweb.entity.AlertaGeocerca;
import com.example.transitweb.entity.Bus;
import com.example.transitweb.entity.Geocerca;
import com.example.transitweb.entity.Telemetria;
import com.example.transitweb.repository.AlertaGeocercaRepository;
import com.example.transitweb.repository.GeocercaRepository;
import com.example.transitweb.repository.TelemetriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class GeocercaService {

    @Autowired
    private GeocercaRepository geocercaRepository;

    @Autowired
    private AlertaGeocercaRepository alertaGeocercaRepository;

    @Autowired
    private TelemetriaRepository telemetriaRepository;

    @Transactional(readOnly = true)
    public List<Geocerca> listarGeocercas() {
        return geocercaRepository.findAll();
    }

    @Transactional
    public Geocerca crearGeocerca(Geocerca g) {
        return geocercaRepository.save(g);
    }

    @Transactional
    public AlertaGeocerca evaluarYCrearAlerta(Bus bus, Geocerca geocerca) {
        // Simplificado: consulta la última telemetría conocida
        Telemetria t = telemetriaRepository.findTopByBusOrderByTimestampRegistroDesc(bus)
                .orElse(null);
        if (t == null) return null;

        double d = distanciaMetros(geocerca.getLatitudCentro(), geocerca.getLongitudCentro(), t.getLatitud(), t.getLongitud());
        if (d > geocerca.getRadioMetros()) {
            AlertaGeocerca alerta = new AlertaGeocerca();
            alerta.setBus(bus);
            alerta.setGeocerca(geocerca);
            alerta.setTipo("DESVIO");
            alerta.setDescripcion("Bus fuera de geocerca " + geocerca.getNombre());
            return alertaGeocercaRepository.save(alerta);
        }
        return null;
    }

    // Haversine aproximado para distancias cortas
    private double distanciaMetros(double lat1, double lon1, double lat2, double lon2) {
        double R = 6371000; // m
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat/2) * Math.sin(dLat/2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(dLon/2) * Math.sin(dLon/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        return R * c;
    }
}


