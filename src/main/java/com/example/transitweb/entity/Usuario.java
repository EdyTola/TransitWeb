package com.example.transitweb.entity;

import jakarta.persistence.*;
import com.example.transitweb.entity.Rol;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.List;

/**
 * Entidad JPA para la tabla 'usuarios'. Implementa UserDetails para Spring Security.
 */
@Entity
@Table(name = "usuarios")
@Data
public class Usuario implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(nullable = false, unique = true, length = 100)
    private String correo;

    @Column(nullable = false)
    private String password; // Debe guardarse hasheada

    // VITAL: Guarda el nombre del enum (ej: "CONDUCTOR") como un String en la BD
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Rol rol;

    // Aplica para el Monedero Digital del PASAJERO
    @Column(name = "saldo_monedero")
    private Double saldoMonedero = 0.0;

    // --- Métodos de Spring Security UserDetails ---

    // Define los permisos del usuario basados en su rol
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(rol.name())); // Retorna el rol como una autoridad (ej: ROLE_CONDUCTOR)
    }

    @Override
    public String getUsername() {
        return correo; // Usamos el correo como nombre de usuario para el login
    }

    // Métodos que siempre deben retornar 'true' a menos que implementes bloqueo
    @Override
    public boolean isAccountNonExpired() { return true; }
    @Override
    public boolean isAccountNonLocked() { return true; }
    @Override
    public boolean isCredentialsNonExpired() { return true; }
    @Override
    public boolean isEnabled() { return true; }

    @Override
    public String getPassword() {
        return password;
    }
}