package com.example.kinoarenaproject.model.entities;

import jakarta.persistence.*;

@Entity(name = "categories")
@Table
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "category")
    private String category;
    @Column(name = "min_age")
    private int minAge;
}
