package com.example.transitweb.repository;

import com.example.transitweb.entity.DocumentoBus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface DocumentoBusRepository extends JpaRepository<DocumentoBus, Long> {
    // Listar documentos que vencen antes de cierta fecha (Alertas del Admin)
    List<DocumentoBus> findByFechaVencimientoBefore(LocalDate date);
}
