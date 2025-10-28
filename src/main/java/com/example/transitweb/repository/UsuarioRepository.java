package com.example.transitweb.repository;

import com.example.transitweb.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    // Método clave usado por loadUserByUsername para el login
    Optional<Usuario> findByCorreo(String correo);

    // Método clave usado por getUsuariosByRol(Rol rol)
    // Spring internamente busca por la columna 'rol' y el valor String proporcionado.
    List<Usuario> findByRol(String rol);
}
