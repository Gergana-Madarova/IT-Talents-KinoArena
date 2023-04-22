package com.example.kinoarenaproject.service;

import com.example.kinoarenaproject.model.DTOs.TicketBookDTO;
import com.example.kinoarenaproject.model.DTOs.TicketInfoDTO;
import com.example.kinoarenaproject.model.entities.Movie;
import com.example.kinoarenaproject.model.entities.Projection;
import com.example.kinoarenaproject.model.entities.Ticket;
import com.example.kinoarenaproject.model.entities.User;
import com.example.kinoarenaproject.model.exceptions.NotFoundException;
import com.example.kinoarenaproject.model.exceptions.UnauthorizedException;
import com.example.kinoarenaproject.model.repositories.ProjectionRepository;
import com.example.kinoarenaproject.model.repositories.TicketRepository;
import com.example.kinoarenaproject.model.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TicketService extends com.example.kinoarenaproject.service.Service {
    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private ProjectionRepository projectionRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper mapper;

    public TicketInfoDTO book(TicketBookDTO bookTicket, int loggedId) {
        Projection projection = checkOptionalIsPresent(projectionRepository.findById(bookTicket.getProjectionId()), "non-existent projection");
        int countExistTicket = ticketRepository.countByProjectionIdAndRowNumberAndColNumber(projection.getId(), bookTicket.getRowNumber(), bookTicket.getColNumber());
        if (countExistTicket != 0) {
            throw new NotFoundException("Choose a free seat! This seat is reserved.");
        }

        Ticket ticket = new Ticket();
        User u = userById(loggedId);
        ticket.setUser(u);
        ticket.setProjection(projection);
        ticket.setRowNumber(bookTicket.getRowNumber());
        ticket.setColNumber(bookTicket.getColNumber());
        ticketRepository.save(ticket);
        return mapper.map(ticket, TicketInfoDTO.class);
    }

    public TicketInfoDTO getTicketById(int id, int idUser) {
        Ticket ticket = checkOptionalIsPresent(ticketRepository.findById(id), "non-existent ticket");
        if (!admin(idUser) || ticket.getUser().getId() != idUser) {
            throw new UnauthorizedException("Unauthorized role!");
        }
        return mapper.map(ticket, TicketInfoDTO.class);
    }
}