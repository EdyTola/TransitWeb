package com.example.transitweb.repository;

import com.example.transitweb.entity.ReporteEvento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReporteEventoRepository extends JpaRepository<ReporteEvento, Long> {
    // Obtener reportes por estado (Abierto, En Atención, Resuelto) para el Admin
    List<ReporteEvento> findByEstado(String estado);
}