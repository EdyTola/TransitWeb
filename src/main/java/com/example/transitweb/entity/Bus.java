package com.example.transitweb.entity;

import jakarta.persistence.*;
import lombok.Data;

/**
 * Entidad JPA para la tabla 'buses'.
 */
@Entity
@Table(name = "buses")
@Data
public class Bus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 10)
    private String placa;

    @Column(nullable = false, length = 20)
    private String estado;

    @Column(name = "tiene_rampa")
    private Boolean tieneRampa = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ruta_actual_id")
    private Ruta rutaActual;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "conductor_id")
    private Usuario conductor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cobrador_id")
    private Usuario cobrador;

}

