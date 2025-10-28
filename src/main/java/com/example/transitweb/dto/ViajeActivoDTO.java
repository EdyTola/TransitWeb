package com.example.transitweb.dto;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * DTO para transferir la información de un Viaje Activo (Dashboard Admin).
 */
@Data
public class ViajeActivoDTO {
    private Long asignacionId;
    private String busPlaca;
    private String conductorNombre;
    private String rutaNombre;
    private LocalDateTime horaInicio;
    private String estado;
}
