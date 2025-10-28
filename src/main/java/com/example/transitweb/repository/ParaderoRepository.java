package com.example.transitweb.repository;

import com.example.transitweb.entity.Paradero;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParaderoRepository extends JpaRepository<Paradero, Long> {
}
