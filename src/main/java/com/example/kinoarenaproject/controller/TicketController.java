package com.example.kinoarenaproject.controller;

import com.example.kinoarenaproject.model.DTOs.TicketBookDTO;
import com.example.kinoarenaproject.model.entities.Ticket;
import com.example.kinoarenaproject.serice.TicketService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class TicketController extends AbstractController{
    @Autowired
    private TicketService ticketService;

    @GetMapping("/tickets/{id}")
    public Ticket getById(@PathVariable int id){
        return ticketService.getById(id);
    }
    @PostMapping("/tickets")
    public Ticket book(@RequestBody TicketBookDTO dto, HttpSession s){
        return ticketService.book(dto, getLoggedId(s));
    }
}
