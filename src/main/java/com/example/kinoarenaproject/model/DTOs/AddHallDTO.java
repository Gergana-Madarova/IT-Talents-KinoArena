package com.example.kinoarenaproject.model.DTOs;

import com.example.kinoarenaproject.model.entities.Cinema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddHallDTO {
    private int typeId;

    private int rows;

    private int columns;

    private int cinemaId;
}
