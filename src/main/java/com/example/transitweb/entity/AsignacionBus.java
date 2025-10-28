package com.example.transitweb.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * Entidad JPA para la tabla 'asignaciones_bus'.
 * Registra los turnos de trabajo y la asignación histórica de personal y flota.
 */
@Entity
@Table(name = "asignaciones_bus")
@Data
public class AsignacionBus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bus_id", nullable = false)
    private Bus bus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "conductor_id", nullable = false)
    private Usuario conductor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cobrador_id")
    private Usuario cobrador; // Puede ser nulo

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ruta_id", nullable = false)
    private Ruta rutaAsignada;

    @Column(name = "hora_inicio", nullable = false)
    private LocalDateTime horaInicio;

    @Column(name = "hora_fin")
    private LocalDateTime horaFin; // Nulo si el turno está activo

    @Column(name = "kilometraje_inicio")
    private Double kilometrajeInicio;

    @Column(name = "kilometraje_fin")
    private Double kilometrajeFin;

    @Column(name = "estado_turno", nullable = false)
    private String estado = "ACTIVO"; // ACTIVO, FINALIZADO, CANCELADO
}