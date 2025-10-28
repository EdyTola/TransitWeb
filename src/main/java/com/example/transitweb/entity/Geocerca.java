package com.example.transitweb.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "geocercas")
@Data
public class Geocerca {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nombre;

    @Column(nullable = false)
    private Double latitudCentro;

    @Column(nullable = false)
    private Double longitudCentro;

    @Column(nullable = false)
    private Double radioMetros; // Radio de la geocerca (círculo simple)

    @Column(name = "zona_critica")
    private Boolean zonaCritica = false;
}


