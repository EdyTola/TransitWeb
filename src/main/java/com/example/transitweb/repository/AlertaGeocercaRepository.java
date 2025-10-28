package com.example.transitweb.repository;

import com.example.transitweb.entity.AlertaGeocerca;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlertaGeocercaRepository extends JpaRepository<AlertaGeocerca, Long> {
}


