package com.example.transitweb.repository;

import com.example.transitweb.entity.Tarifa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TarifaRepository extends JpaRepository<Tarifa, Long> {
    // Obtener la tarifa actual para un tipo específico (Ej. "Adulto")
    Optional<Tarifa> findByTipoPasajeroAndEstaActivaIsTrue(String tipoPasajero);
}