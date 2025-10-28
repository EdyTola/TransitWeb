package com.example.transitweb.repository;

import com.example.transitweb.entity.MantenimientoBus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MantenimientoBusRepository extends JpaRepository<MantenimientoBus, Long> {
}


