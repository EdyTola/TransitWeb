package com.example.transitweb.dto;

import lombok.Data;

/**
 * DTO para la solicitud de pago de pasaje (usado por el cobrador/sistema de a bordo).
 */
@Data
public class PagoPasajeRequestDTO {
    private Long pasajeroId;
    private Long tarifaId; // ID de la tarifa aplicada (Adulto, Escolar, etc.)
    private Long asignacionId; // El turno de bus actual
}