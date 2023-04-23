package com.example.kinoarenaproject.controller;

import com.example.kinoarenaproject.model.DTOs.*;
import com.example.kinoarenaproject.service.TicketService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class TicketController extends AbstractController {
    @Autowired
    private TicketService ticketService;

    @GetMapping("/tickets/{id}")
    public TicketInfoDTO getById(@PathVariable int id, HttpSession session) {
        int idUser = loggedId(session);
        return ticketService.getTicketById(id, idUser);
    }

    @PostMapping("/tickets")
    public TicketInfoDTO book(@RequestBody @Valid TicketBookDTO dto, HttpSession session) {
        return ticketService.book(dto, loggedId(session));
    }
}
