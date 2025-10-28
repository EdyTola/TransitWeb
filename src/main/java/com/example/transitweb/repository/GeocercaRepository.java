package com.example.transitweb.repository;

import com.example.transitweb.entity.Geocerca;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GeocercaRepository extends JpaRepository<Geocerca, Long> {
}


