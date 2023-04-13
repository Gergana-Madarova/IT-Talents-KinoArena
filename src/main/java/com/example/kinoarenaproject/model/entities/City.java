package com.example.kinoarenaproject.model.entities;

import jakarta.persistence.*;

@Entity(name = "cities")
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "name")
    private String name;
    @Column(name = "postcode")
    private String postcode;
}
