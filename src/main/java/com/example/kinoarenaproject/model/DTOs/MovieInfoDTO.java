package com.example.kinoarenaproject.model.DTOs;

import com.example.kinoarenaproject.model.entities.Category;
import com.example.kinoarenaproject.model.entities.Genre;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MovieInfoDTO {
    private int id;
    private String title;
    private String description;
    private int duration;
    private LocalDate premiere;
    private String director;
    private String cast;
    private CategoryInfo category;
    private GenreInfo genre;
}
