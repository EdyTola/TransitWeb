package com.example.transitweb.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * Entidad JPA para la tabla 'reportes_evento'.
 * Registra eventos en la ruta (accidentes, tráfico, obras, etc.) reportados por usuarios.
 */
@Entity
@Table(name = "reportes_evento")
@Data
public class ReporteEvento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_reporta_id", nullable = false)
    private Usuario usuarioReporta;

    @Column(nullable = false)
    private String tipo; // Ej: Accidente, Bloqueo de Tráfico, Desvío, Obras

    @Column(nullable = false, columnDefinition = "TEXT")
    private String descripcion;

    @Column
    private Double latitud;

    @Column
    private Double longitud;

    @Column(name = "fecha_hora_reporte", nullable = false)
    private LocalDateTime fechaHoraReporte = LocalDateTime.now();

    @Column(name = "estado_reporte", nullable = false)
    private String estado = "ACTIVO"; // ACTIVO, RESUELTO, CERRADO
}
