package com.example.transitweb.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PagoQrDTO {
    private String payload; // Cadena a codificar como QR en el frontend
}


