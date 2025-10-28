package com.example.transitweb.config;

import com.example.transitweb.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

/**
 * Configuración de Spring Security para la aplicación.
 * Define el PasswordEncoder, el AuthenticationManager y las reglas de acceso.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private UsuarioService usuarioService; // El servicio que implementa UserDetailsService

    /**
     * Define el bean de PasswordEncoder (BCrypt recomendado).
     * Este bean es usado por UsuarioService y Spring Security.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Define y expone el bean de AuthenticationManager.
     * Esto resuelve el error en UsuarioController.
     */
    @Bean
    public AuthenticationManager authenticationManager(PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        // 1. Asigna el servicio que carga los usuarios (UsuarioService)
        authProvider.setUserDetailsService(usuarioService);
        // 2. Asigna el codificador de contraseñas
        authProvider.setPasswordEncoder(passwordEncoder);
        return new ProviderManager(authProvider);
    }

    /**
     * Configura las reglas de seguridad HTTP (autorización de endpoints).
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable) // Desactivar CSRF para APIs REST
                .cors(cors -> cors.configurationSource(corsConfigurationSource())) // Habilitar CORS
                .authorizeHttpRequests(auth -> auth
                        // Acceso público para el login y recarga (simulando pasarela de pago)
                        .requestMatchers("/api/usuarios/login").permitAll()
                        .requestMatchers("/api/transacciones/recarga").permitAll() // Se puede restringir más tarde

                        // Endpoints para Pasajero y Conductor
                        .requestMatchers("/api/reportes").hasAnyAuthority("PASAJERO", "CONDUCTOR")
                        .requestMatchers("/api/transacciones/pago-pasaje").hasAnyAuthority("CONDUCTOR") // Lo usaría el dispositivo del bus

                        // Endpoints para Administrador
                        .requestMatchers("/api/usuarios/registrar/conductor").hasAuthority("ADMIN")
                        .requestMatchers("/api/usuarios/rol/conductor").hasAuthority("ADMIN")
                        .requestMatchers("/api/reportes/abiertos", "/api/reportes/{id}/estado").hasAuthority("ADMIN")

                        // Cualquier otra solicitud requiere autenticación (autenticado, no importa el rol)
                        .anyRequest().authenticated()
                );

        return http.build();
    }

    /**
     * Configuración básica de CORS para desarrollo.
     * Permite cualquier origen, método y cabecera.
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*")); // Permite todos los orígenes
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("*")); // Permite todas las cabeceras
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
