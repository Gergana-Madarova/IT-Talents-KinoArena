package com.example.kinoarenaproject.model.entities;

import jakarta.persistence.*;

import java.sql.Date;
import java.sql.Time;

@Entity(name = "projections")
public class Projection {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "start_time")
    private Time startTime;
    @Column(name = "date")
    private Date date;
    //TODO HAll
 /*   @ManyToOne
    @JoinColumn(name = "hall_id")
    private Hall hallId; */
    @ManyToOne
    @JoinColumn(name = "movie_id")
    private Movie movieId;
}
