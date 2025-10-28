package com.example.transitweb.repository;

import com.example.transitweb.entity.Paradero;
import com.example.transitweb.entity.Ruta;
import com.example.transitweb.entity.RutaParadero;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RutaParaderoRepository extends JpaRepository<RutaParadero, Long> {
    // Obtener la secuencia ordenada de paraderos para una ruta
    List<RutaParadero> findByRutaOrderByOrdenAsc(Ruta ruta);

    Optional<RutaParadero> findByRutaAndParadero(Ruta ruta, Paradero paradero);

}