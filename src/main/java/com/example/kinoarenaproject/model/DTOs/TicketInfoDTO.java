package com.example.kinoarenaproject.model.DTOs;

import com.example.kinoarenaproject.model.entities.Projection;
import com.example.kinoarenaproject.model.entities.User;

// TODO трябва ли да го имам, като има всички полета на Ticket?
public class TicketInfoDTO {
    private int id;
    private User user;
    private boolean isRegular;
    private Projection projection;
    private int rowNumber;
    private int colNumber;
    private double price;
    private double discount;
}
