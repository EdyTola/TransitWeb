package com.example.transitweb.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * Entidad JPA para la tabla 'telemetria'.
 */
@Entity
@Table(name = "telemetria")
@Data
public class Telemetria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bus_id", nullable = false)
    private Bus bus;

    @Column(nullable = false)
    private Double latitud;

    @Column(nullable = false)
    private Double longitud;

    @Column(nullable = false)
    private Double velocidad;

    @Column(name = "pasajeros_actuales")
    private Integer pasajerosActuales = 0;

    @Column(name = "timestamp_registro", nullable = false)
    private LocalDateTime timestampRegistro = LocalDateTime.now();

}