package com.example.kinoarenaproject.serice;

import com.example.kinoarenaproject.model.DTOs.TicketBookDTO;
import com.example.kinoarenaproject.model.entities.Ticket;
import com.example.kinoarenaproject.model.exceptions.NotFoundException;
import com.example.kinoarenaproject.model.repositories.TicketRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TicketService {
    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private ModelMapper mapper;

    public Ticket book(TicketBookDTO ticketDTO, int loggedId) {
        boolean isReserved = ticketRepository.existsByRowNumberAndColNumber(ticketDTO.getRowNumber(), ticketDTO.getColNumber());
        if (isReserved) {
            throw new NotFoundException("Choose a free seat!\nThis seat is reserved.");
        }
        Ticket ticket = mapper.map(ticketDTO, Ticket.class);
        ticketRepository.save(ticket);
        return ticket;
    }
    public Ticket getById(int id) {
        Optional opt = ticketRepository.findById(id);
        if (opt.isPresent()) {
            return mapper.map(opt.get(), Ticket.class);
        } else {
            throw new NotFoundException("Ticket with this id is not found");
        }
    }
}
