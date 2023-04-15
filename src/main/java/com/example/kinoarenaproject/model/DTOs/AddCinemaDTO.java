package com.example.kinoarenaproject.model.DTOs;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddCinemaDTO {

    private String name;
    private String adress;
    private String phoneNumber;
}
