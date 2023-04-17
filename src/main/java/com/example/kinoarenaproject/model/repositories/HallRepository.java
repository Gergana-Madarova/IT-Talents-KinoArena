package com.example.kinoarenaproject.model.repositories;

import com.example.kinoarenaproject.model.entities.Cinema;
import com.example.kinoarenaproject.model.entities.Hall;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HallRepository extends JpaRepository<Hall,Integer> {
    List<Hall> findByCinema(Cinema cinema);
//    List<Cinema> findByCity(int city);
}
