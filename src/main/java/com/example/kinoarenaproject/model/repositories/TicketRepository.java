package com.example.kinoarenaproject.model.repositories;

import com.example.kinoarenaproject.model.entities.Ticket;
import com.example.kinoarenaproject.model.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Integer> {
    boolean existsByRowNumberAndColNumber(int rowNumber, int colNumber);
    Optional<Ticket> findById(int id);
}
