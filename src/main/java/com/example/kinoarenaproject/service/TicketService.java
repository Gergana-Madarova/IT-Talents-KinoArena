package com.example.kinoarenaproject.service;

import com.example.kinoarenaproject.controller.Constants;
import com.example.kinoarenaproject.model.DTOs.TicketBookDTO;
import com.example.kinoarenaproject.model.DTOs.TicketInfoDTO;
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

import java.util.Objects;
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
        User u = userById(loggedId);
        //TODO да променя ролята на USER
        if (!u.getRole_name().equals(Constants.ADMIN)) {
            throw new UnauthorizedException("Unauthorized role");
        }
        Optional<Projection> opt = projectionRepository.findById(bookTicket.getProjectionId());
        if (!opt.isPresent()) {
            throw new NotFoundException("Projection not found");
        }
        Projection projection = opt.get();
        int countExistTicket = ticketRepository.countByProjectionIdAndRowNumberAndColNumber(projection.getId(), bookTicket.getRowNumber(), bookTicket.getColNumber());
        if (countExistTicket != 0) {
            throw new NotFoundException("Choose a free seat! This seat is reserved.");
        }

        Ticket ticket = new Ticket();
        ticket.setUser(u);
        ticket.setProjection(projection);
        ticket.setRegular(bookTicket.isRegular());
        ticket.setRowNumber(bookTicket.getRowNumber());
        ticket.setColNumber(bookTicket.getColNumber());
        ticket.setPrice(bookTicket.getPrice());
        ticket.setDiscount(bookTicket.getDiscount());

        ticketRepository.save(ticket);
        return mapper.map(ticket, TicketInfoDTO.class);
    }

    public TicketInfoDTO getTicketById(int id) {
        Optional<Ticket> opt = ticketRepository.findById(id);
        if (opt.isPresent()) {
            Ticket ticket = opt.get();
            return mapper.map(ticket, TicketInfoDTO.class);
        } else {
            throw new NotFoundException("Ticket with this id is not found");
        }
    }
}
