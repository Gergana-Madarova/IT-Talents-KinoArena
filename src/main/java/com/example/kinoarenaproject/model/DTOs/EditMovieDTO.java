package com.example.kinoarenaproject.model.DTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EditMovieDTO {
    private int id;
    private String title;
    private String description;
    private Integer duration;
    private LocalDate premiere;
    private String director;
    private String cast;
    private int category;
    private int genre;
}
