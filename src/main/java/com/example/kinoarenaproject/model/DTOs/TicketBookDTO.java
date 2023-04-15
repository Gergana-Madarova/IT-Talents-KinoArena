package com.example.kinoarenaproject.model.DTOs;

import com.example.kinoarenaproject.model.entities.Projection;
import com.example.kinoarenaproject.model.entities.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TicketBookDTO {
    private User user;
    private boolean isRegular;
    private Projection projection;
    private int rowNumber;
    private int colNumber;
    private double price;
    private double discount;
}
