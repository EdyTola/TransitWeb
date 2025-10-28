package com.example.transitweb.dto;

import lombok.Data;

/**
 * DTO para la solicitud de envío de telemetría desde el bus (Conductor).
 */
@Data
public class TelemetriaRequestDTO {
    private Long asignacionId; // El ID del turno activo
    private Double latitud;
    private Double longitud;
    private Integer ocupacion; // Número de pasajeros
    private Double velocidad;
}
