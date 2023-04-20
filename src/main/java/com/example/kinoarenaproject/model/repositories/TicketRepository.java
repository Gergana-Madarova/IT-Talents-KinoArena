package com.example.kinoarenaproject.model.repositories;

import com.example.kinoarenaproject.model.entities.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Integer> {
    int countByProjectionIdAndRowNumberAndColNumber(int projectionId, int rowNumber, int colNumber);

    Optional<Ticket> findById(int id);
}
