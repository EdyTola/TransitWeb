package com.example.transitweb.dto;

import lombok.Data;

@Data
public class ReporteEventoRequestDTO {
    private Long usuarioId; // Pasajero o Conductor que reporta
    private String tipo; // Ej: Tráfico, Accidente
    private String descripcion;
    private Double latitud;
    private Double longitud;
}
