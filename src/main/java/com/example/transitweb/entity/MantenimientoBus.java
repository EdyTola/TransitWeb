package com.example.transitweb.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Entity
@Table(name = "mantenimientos_bus")
@Data
public class MantenimientoBus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bus_id", nullable = false)
    private Bus bus;

    @Column(nullable = false)
    private String descripcion;

    @Column(name = "fecha_reportada", nullable = false)
    private LocalDate fechaReportada = LocalDate.now();

    @Column(nullable = false)
    private String estado = "ABIERTA"; // ABIERTA | EN_PROCESO | CERRADA
}


