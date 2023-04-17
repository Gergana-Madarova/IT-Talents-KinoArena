package com.example.kinoarenaproject.model.repositories;

import com.example.kinoarenaproject.model.entities.Projection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProjectionRepository extends JpaRepository<Projection, Integer> {
    Optional<Projection> findById(int id);
}
