package com.example.transitweb.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "alertas_geocerca")
@Data
public class AlertaGeocerca {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bus_id", nullable = false)
    private Bus bus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "geocerca_id", nullable = false)
    private Geocerca geocerca;

    @Column(nullable = false)
    private String tipo; // DESVIO | DETENCION

    @Column
    private String descripcion;

    @Column(nullable = false)
    private String estado = "NUEVA"; // NUEVA | EN_ATENCION | RESUELTA

    @Column(name = "fecha_hora", nullable = false)
    private LocalDateTime fechaHora = LocalDateTime.now();
}


