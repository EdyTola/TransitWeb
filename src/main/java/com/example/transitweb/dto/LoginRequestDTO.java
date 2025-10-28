package com.example.transitweb.dto;

import lombok.Data;

/**
 * DTO para la solicitud de inicio de sesión.
 */
@Data
public class LoginRequestDTO {
    private String correo;
    private String password;
}

