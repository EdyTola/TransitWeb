package com.example.transitweb.entity;

import jakarta.persistence.*;
import lombok.Data;

/**
 * Entidad JPA para la tabla 'rutas'.
 */
@Entity
@Table(name = "rutas")
@Data
public class Ruta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String nombre;

    @Column(name = "color_hex", nullable = false, length = 7)
    private String colorHex;

    @Column(nullable = false, unique = true, length = 10)
    private String codigo;
}