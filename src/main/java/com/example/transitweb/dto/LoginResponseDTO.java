package com.example.transitweb.dto;

import com.example.transitweb.entity.Rol;
import lombok.Data;

/**
 * DTO para la respuesta de inicio de sesión exitosa.
 */
@Data
public class LoginResponseDTO {
    private String token;
    private Long userId;
    private String nombreCompleto;
    private Rol rol;
}