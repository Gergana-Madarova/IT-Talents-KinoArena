package com.example.kinoarenaproject.model.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "cinemas")
@Table
@Setter
@Getter
public class Cinema {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private String name;
    @Column
    private String adress;
    @ManyToOne
    @JoinColumn(name = "city_id")
    private City city;


}
