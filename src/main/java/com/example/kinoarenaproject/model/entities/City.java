package com.example.kinoarenaproject.model.entities;

import jakarta.persistence.*;

import java.util.List;

@Entity(name = "cities")
@Table
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "name")
    private String name;
    @Column(name = "postcode")
    private String postcode;
//    @OneToMany(mappedBy = "city")
//    private List<User>users;
}
