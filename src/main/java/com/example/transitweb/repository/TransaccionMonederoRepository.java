package com.example.transitweb.repository;

import com.example.transitweb.entity.TransaccionMonedero;
import com.example.transitweb.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransaccionMonederoRepository extends JpaRepository<TransaccionMonedero, Long> {
    // Obtener el historial de transacciones de un pasajero
    List<TransaccionMonedero> findByPasajeroOrderByFechaHoraTransaccionDesc(Usuario pasajero);
}
