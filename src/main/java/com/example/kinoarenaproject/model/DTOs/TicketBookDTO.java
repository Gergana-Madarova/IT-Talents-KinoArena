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
    //private int id;
    private int projectionId;
    private int rowNumber;
    private int colNumber;
}
