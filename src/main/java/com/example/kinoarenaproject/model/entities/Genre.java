package com.example.kinoarenaproject.model.entities;

import jakarta.persistence.*;

@Entity(name = "genres")
@Table
public class Genre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "genre")
    private String genre;
}
