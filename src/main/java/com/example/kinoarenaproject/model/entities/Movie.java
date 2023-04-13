package com.example.kinoarenaproject.model.entities;

import jakarta.persistence.*;

import java.sql.Date;
import java.sql.Time;

@Entity(name = "movies")
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "title")
    private String title;
    @Column(name = "description")
    private String description;
    @Column(name = "duration")
    private int duration;
    @Column(name = "premiere")
    private Date premiere;
    @Column(name = "director")
    private String director;
    @Column(name = "cast")
    private String cast;
    @OneToOne
    @JoinColumn(name = "category_id")
    private Category category;
    @OneToOne
    @JoinColumn(name = "genre_id")
    private Genre genre;
}
