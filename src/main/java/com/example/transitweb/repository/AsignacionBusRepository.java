package com.example.transitweb.repository;

import com.example.transitweb.entity.AsignacionBus;
import com.example.transitweb.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AsignacionBusRepository extends JpaRepository<AsignacionBus, Long> {
    // Encontrar la asignación activa de un conductor (horaFin es nulo)
    Optional<AsignacionBus> findByConductorAndHoraFinIsNull(Usuario conductor);

    // Listar todas las asignaciones activas para el Dashboard del Admin
    List<AsignacionBus> findByEstado(String estado);
}