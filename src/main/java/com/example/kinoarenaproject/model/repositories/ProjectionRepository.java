package com.example.kinoarenaproject.model.repositories;

import com.example.kinoarenaproject.model.entities.Projection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectionRepository extends JpaRepository<Projection, Integer> {
}
