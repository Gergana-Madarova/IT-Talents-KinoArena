package com.example.kinoarenaproject.model.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "halls")
@Table
@Setter
@Getter
public class Hall {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
//    @Column(name = "cinema_id")
//    private  int cinemaId;
    @Column(name = "type_id")
    private int typeId;
    @Column
    private int rows;
    @Column
    private int columns;
   @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cinemaId")
    private Cinema cinema;
}
