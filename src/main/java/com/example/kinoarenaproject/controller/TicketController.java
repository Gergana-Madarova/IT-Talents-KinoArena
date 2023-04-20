package com.example.kinoarenaproject.controller;

import com.example.kinoarenaproject.model.DTOs.*;
import com.example.kinoarenaproject.service.TicketService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class TicketController extends AbstractController {
    @Autowired
    private TicketService ticketService;

    @GetMapping("/tickets/{id}")
    public TicketInfoDTO getById(@PathVariable int id, HttpSession session) {
     /*   boolean logged = (boolean) session.getAttribute(Constants.LOGGED);
        if (!logged) {
            throw new UnauthorizedException("You have to login");
        } else {
            //TODO да се виждат ли само билетите на този user?
           // int idUser = loggedId(session); */
        return ticketService.getTicketById(id);

    }

    @PostMapping("/tickets")
    public TicketInfoDTO book(@RequestBody TicketBookDTO dto, HttpSession session) {
        return ticketService.book(dto, loggedId(session));
    }
}
