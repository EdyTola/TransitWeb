package com.example.transitweb.dto;

import lombok.Data;

/**
 * DTO para la solicitud de recarga de monedero por parte del pasajero.
 */
@Data
public class RecargaMonederoRequestDTO {
    private Long pasajeroId;
    private Double monto;
    private String metodoPago; // Ej: Tarjeta, Efectivo
}

