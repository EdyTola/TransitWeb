package com.example.transitweb.dto;

import lombok.Data;

/**
 * DTO para transferir información básica de un conductor/cobrador.
 */
@Data
public class PersonalDTO {
    private Long id;
    private String nombreCompleto;
    private String licencia;
    private String telefono;
}
