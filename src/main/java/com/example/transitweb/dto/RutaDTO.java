package com.example.transitweb.dto;

import lombok.Data;

/**
 * DTO para listar las rutas de manera sencilla.
 */
@Data
public class RutaDTO {
    private Long id;
    private String nombre; // Ej: Rojo 18
    private String codigo; // Ej: R18
    private String color; // Ej: #FF0000
    private int totalParaderos;
}