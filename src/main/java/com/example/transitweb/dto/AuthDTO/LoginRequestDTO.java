package com.example.transitweb.dto.AuthDTO;

public class LoginRequestDTO {

    private String correo;
    private String password;

    // Constructor sin argumentos (necesario para Spring/Jackson)
    public LoginRequestDTO() {
    }

    // Constructor con argumentos
    public LoginRequestDTO(String correo, String password) {
        this.correo = correo;
        this.password = password;
    }

    // --- Getters y Setters ---

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
