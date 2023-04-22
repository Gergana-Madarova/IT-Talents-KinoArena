package com.example.kinoarenaproject.model.DTOs;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EditProjectionDTO {
    @DateTimeFormat(pattern = "HH:mm:ss")
    private LocalTime startTime;
    @Future(message = "Date should be in the future")
    private LocalDate date;
    @Positive(message = "Price should be a positive value")
    private double price;
    @Positive(message = "Hall ID should be a positive value")
    private int hallId;
    @Positive(message = "Movie ID should be a positive value")
    private int movieId;
}
