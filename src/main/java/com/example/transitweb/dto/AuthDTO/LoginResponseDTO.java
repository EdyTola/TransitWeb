package com.example.transitweb.dto.AuthDTO;

public class LoginResponseDTO {

    private Long id;
    private String correo;
    private String rol;
    private String token; // Aquí se devolvería el token JWT real

    // Constructor con argumentos
    public LoginResponseDTO(Long id, String correo, String rol, String token) {
        this.id = id;
        this.correo = correo;
        this.rol = rol;
        this.token = token;
    }

    // Constructor sin argumentos (necesario para Spring/Jackson)
    public LoginResponseDTO() {
    }

    // --- Getters y Setters ---

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}