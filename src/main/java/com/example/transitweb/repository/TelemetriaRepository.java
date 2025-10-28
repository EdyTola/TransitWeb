package com.example.transitweb.repository;

import com.example.transitweb.entity.Bus;
import com.example.transitweb.entity.Telemetria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TelemetriaRepository extends JpaRepository<Telemetria, Long> {
    // Obtener la última posición conocida de un bus
    Optional<Telemetria> findTopByBusOrderByFechaHoraRegistroDesc(Bus bus);
}
