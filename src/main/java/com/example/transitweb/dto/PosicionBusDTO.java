package com.example.transitweb.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PosicionBusDTO {
    private Long busId;
    private String placa;
    private String rutaNombre;
    private Double latitud;
    private Double longitud;
    private Integer ocupacionPorcentaje;
    private LocalDateTime timestamp; // Momento del registro
}