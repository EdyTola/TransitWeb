package com.example.transitweb.service;

import com.example.transitweb.entity.Usuario;
import com.example.transitweb.entity.Rol; // Importación directa del Enum
import com.example.transitweb.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * Servicio para la lógica de negocio de la entidad Usuario.
 * Implementa UserDetailsService para ser usado por Spring Security en el login.
 */
@Service
public class UsuarioService implements UserDetailsService {

    // Asegúrate de que este bean se esté inyectando correctamente (requiere SecurityConfig)
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder; // Si sigue fallando aquí, revisa SecurityConfig.java

    // --- Métodos de Spring Security ---

    /**
     * Carga un Usuario por su correo (username) para la autenticación.
     */
    @Override
    public UserDetails loadUserByUsername(String correo) throws UsernameNotFoundException {
        // Devuelve nuestra entidad Usuario que implementa UserDetails
        return usuarioRepository.findByCorreo(correo)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con correo: " + correo));
    }

    // --- Métodos CRUD y Lógica de Negocio ---

    @Transactional(readOnly = true)
    public Usuario getUsuarioById(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Usuario con ID " + id + " no encontrado."));
    }

    @Transactional(readOnly = true)
    public List<Usuario> getAllUsuarios() {
        return usuarioRepository.findAll();
    }

    /**
     * Obtiene la lista de usuarios filtrada por un Rol específico (ej: CONDUCTOR, PASAJERO).
     */
    @Transactional(readOnly = true)
    public List<Usuario> getUsuariosByRol(Rol rol) {
        // En este punto, el repositorio debe tener un método findByRol(String rol)
        // Usamos rol.name() para pasar el nombre del Enum como String a la consulta SQL/JPA
        return usuarioRepository.findByRol(rol.name());
    }

    @Transactional
    public Usuario createConductor(Usuario usuario) {
        // Encriptar la contraseña antes de guardar
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        usuario.setRol(Rol.CONDUCTOR); // Asignación directa del Enum
        usuario.setSaldoMonedero(0.0);
        return usuarioRepository.save(usuario);
    }

    @Transactional
    public Usuario createPasajero(Usuario usuario) {
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        usuario.setRol(Rol.PASAJERO);
        if (usuario.getSaldoMonedero() == null) {
            usuario.setSaldoMonedero(0.0);
        }
        return usuarioRepository.save(usuario);
    }

    @Transactional
    public Usuario createAdmin(Usuario usuario) {
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        usuario.setRol(Rol.ADMIN);
        usuario.setSaldoMonedero(0.0);
        return usuarioRepository.save(usuario);
    }

    @Transactional
    public Usuario updateUsuario(Long id, Usuario detallesUsuario) {
        Usuario usuario = getUsuarioById(id);

        // Actualizar campos
        usuario.setNombre(detallesUsuario.getNombre());
        usuario.setCorreo(detallesUsuario.getCorreo());
        // Solo actualizar la contraseña si se proporciona una nueva
        if (detallesUsuario.getPassword() != null && !detallesUsuario.getPassword().isEmpty()) {
            usuario.setPassword(passwordEncoder.encode(detallesUsuario.getPassword()));
        }

        return usuarioRepository.save(usuario);
    }

    @Transactional
    public void deleteUsuario(Long id) {
        usuarioRepository.deleteById(id);
    }

    // --- Lógica de Monedero ---

    @Transactional
    public Usuario actualizarSaldo(Long usuarioId, double monto) {
        Usuario usuario = getUsuarioById(usuarioId);
        double nuevoSaldo = usuario.getSaldoMonedero() + monto;

        if (nuevoSaldo < 0) {
            throw new IllegalArgumentException("Saldo insuficiente para la operación.");
        }

        usuario.setSaldoMonedero(nuevoSaldo);
        return usuarioRepository.save(usuario);
    }
}