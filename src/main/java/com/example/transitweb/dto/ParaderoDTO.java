package com.example.transitweb.dto;

import lombok.Data;

/**
 * DTO para los puntos clave (Paraderos).
 */
@Data
public class ParaderoDTO {
    private Long id;
    private String nombre;
    private Double latitud;
    private Double longitud;
    private int secuencia; // Orden en la ruta
}