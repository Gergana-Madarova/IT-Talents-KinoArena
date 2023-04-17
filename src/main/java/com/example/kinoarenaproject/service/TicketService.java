package com.example.kinoarenaproject.service;

import com.example.kinoarenaproject.controller.Constants;
import com.example.kinoarenaproject.model.DTOs.CinemaDTO;
import com.example.kinoarenaproject.model.DTOs.ProjectionDTO;
import com.example.kinoarenaproject.model.DTOs.TicketBookDTO;
import com.example.kinoarenaproject.model.DTOs.TicketInfoDTO;
import com.example.kinoarenaproject.model.entities.Cinema;
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
        Ticket ticket = null;
        try {
            User u = userById(loggedId);
            //TODO да променя ролята на USER
            if (!u.getRole_name().equals(Constants.ADMIN)) {
                throw new UnauthorizedException("Unauthorized role");
            }
            boolean isReserved = ticketRepository.existsByRowNumberAndColNumber(bookTicket.getRowNumber(), bookTicket.getColNumber());
            if (isReserved) {
                throw new NotFoundException("Choose a free seat!\nThis seat is reserved.");
            }
            ticket = mapper.map(bookTicket, Ticket.class);
            ticket.setUser(u);

            Optional<Projection> opt = projectionRepository.findById(bookTicket.getProjectionId());
            if (!opt.isPresent()) {
                throw new NotFoundException("Projection not found");
            }
            Projection projection = opt.get();
            ticket.setProjectionId(projection);

            ticketRepository.save(ticket);
        } catch (RuntimeException r) {
            r.printStackTrace();
            System.out.println(r.getMessage());
        }
        return mapper.map(ticket, TicketInfoDTO.class);
    }

    //TODO да го махна от тук и да използвам на Таня, когато го направи
 /*   public User userById(int id) {
        Optional<User> opt = userRepository.findById(id);
        if (!opt.isPresent()) {
            throw new UnauthorizedException("Wrong credentials");
        }
        User u = opt.get();
        return u;
    }

  */

    public TicketInfoDTO getTicketById(int id) {
        Optional<Ticket> opt = ticketRepository.findById(id);
        if (opt.isPresent()) {
            Ticket ticket = opt.get();
            if (Objects.isNull(ticket.getDiscount())){
                ticket.setDiscount(0);
            }
            return mapper.map(ticket, TicketInfoDTO.class);
        } else {
            throw new NotFoundException("Ticket with this id is not found");
        }
    }
}
