package com.example.transitweb.controller;

import com.example.transitweb.dto.AuthDTO.LoginRequestDTO;
import com.example.transitweb.dto.AuthDTO.LoginResponseDTO;
import com.example.transitweb.entity.Usuario;
import com.example.transitweb.entity.Rol;
import com.example.transitweb.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import java.util.List;

/**
 * Controlador REST para la gestión de Usuarios y el flujo de Autenticación.
 * NOTA: La generación del Token JWT se manejaría en una clase de utilidades JWT aparte,
 * pero aquí solo simulamos la autenticación exitosa.
 */
@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    // Suponiendo que el AuthenticationManager está configurado en SecurityConfig
    @Autowired
    private AuthenticationManager authenticationManager;

    // --- Endpoints de Autenticación (Login) ---

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequestDTO loginRequest) {
        try {
            // Intenta autenticar al usuario usando Spring Security
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getCorreo(), loginRequest.getPassword())
            );

            // Si la autenticación es exitosa, se podría generar un token JWT aquí.
            // Por ahora, solo devolvemos un DTO de éxito con el ID y Rol del usuario.
            Usuario usuario = (Usuario) authentication.getPrincipal();

            LoginResponseDTO response = new LoginResponseDTO(
                    usuario.getId(),
                    usuario.getCorreo(),
                    usuario.getRol().name(),
                    "TOKEN_JWT_GENERADO_EXITOSAMENTE" // Placeholder para el token real
            );

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            // Maneja credenciales inválidas
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales de acceso inválidas.");
        }
    }

    // --- Endpoints CRUD y Registro ---

    @PostMapping("/registrar/conductor")
    public ResponseEntity<Usuario> registerConductor(@RequestBody Usuario usuario) {
        // La validación de que solo el ADMIN pueda crear Conductores se haría en SecurityConfig
        Usuario nuevoConductor = usuarioService.createConductor(usuario);
        return new ResponseEntity<>(nuevoConductor, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> getUsuarioById(@PathVariable Long id) {
        Usuario usuario = usuarioService.getUsuarioById(id);
        return ResponseEntity.ok(usuario);
    }

    // Endpoint para Administradores: Obtener todos los conductores
    @GetMapping("/rol/conductor")
    public ResponseEntity<List<Usuario>> getConductores() {
        // La restricción de acceso por rol (ej: solo ADMIN) se debe configurar en Spring Security
        List<Usuario> conductores = usuarioService.getUsuariosByRol(Rol.CONDUCTOR);
        return ResponseEntity.ok(conductores);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Usuario> updateUsuario(@PathVariable Long id, @RequestBody Usuario usuario) {
        Usuario usuarioActualizado = usuarioService.updateUsuario(id, usuario);
        return ResponseEntity.ok(usuarioActualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUsuario(@PathVariable Long id) {
        usuarioService.deleteUsuario(id);
        return ResponseEntity.noContent().build();
    }

    // Endpoint para Pasajeros: Recarga de saldo (Usado por TransaccionMonederoService)
    @PutMapping("/{id}/saldo")
    public ResponseEntity<Usuario> actualizarSaldo(@PathVariable Long id, @RequestParam double monto) {
        // Este endpoint es una operación directa, aunque en un entorno real sería POST a /transacciones/recarga
        // Lo dejamos aquí para testear la funcionalidad simple del saldo.
        Usuario usuario = usuarioService.actualizarSaldo(id, monto);
        return ResponseEntity.ok(usuario);
    }
}