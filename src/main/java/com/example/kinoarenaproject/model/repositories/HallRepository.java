package com.example.kinoarenaproject.model.repositories;

import com.example.kinoarenaproject.model.entities.Hall;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HallRepository extends JpaRepository<Hall,Integer> {

}
