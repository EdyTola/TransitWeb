package com.example.transitweb.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * Entidad JPA para la tabla 'tarifas'.
 * Permite al administrador definir y modificar el costo de los pasajes por categoría (Adulto, Escolar, etc.).
 */
@Entity
@Table(name = "tarifas")
@Data
public class Tarifa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tipo_pasajero", nullable = false, unique = true, length = 50)
    private String tipoPasajero; // Ej: Adulto, Escolar, Universitario

    @Column(nullable = false)
    private Double monto; // Costo actual del pasaje

    @Column(name = "fecha_actualizacion", nullable = false)
    private LocalDateTime fechaActualizacion = LocalDateTime.now();

    @Column(name = "esta_activa", nullable = false)
    private Boolean estaActiva = true;
}