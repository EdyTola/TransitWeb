package com.example.transitweb.entity;

import jakarta.persistence.*;
import lombok.Data;

/**
 * Entidad de unión para definir la secuencia de paraderos en una Ruta (N:M).
 */
@Entity
@Table(name = "ruta_paradero")
@Data
public class RutaParadero {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ruta_id", nullable = false)
    private Ruta ruta;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paradero_id", nullable = false)
    private Paradero paradero;

    @Column(name = "orden_ruta", nullable = false)
    private Integer orden; // El orden en el que se visita este paradero
}