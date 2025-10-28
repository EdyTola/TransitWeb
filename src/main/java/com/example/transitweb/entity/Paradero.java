package com.example.transitweb.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "paraderos")
@Data
public class Paradero {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(nullable = false)
    private Double latitud;

    @Column(nullable = false)
    private Double longitud;

    @Column(nullable = false)
    private Boolean esPrincipal = false; // Indica si es un punto de inicio/fin importante
}

