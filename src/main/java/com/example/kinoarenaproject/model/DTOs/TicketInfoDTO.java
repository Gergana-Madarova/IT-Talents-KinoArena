package com.example.kinoarenaproject.model.DTOs;

import com.example.kinoarenaproject.model.entities.Projection;
import com.example.kinoarenaproject.model.entities.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// TODO трябва ли да го имам, като има всички полета на Ticket?
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TicketInfoDTO {
    private int id;
    private UserWithoutPasswordDTO user;
    private boolean isRegular;
    private ProjectionDTO projection;
    private int rowNumber;
    private int colNumber;
    private double price;
    private double discount;
}
