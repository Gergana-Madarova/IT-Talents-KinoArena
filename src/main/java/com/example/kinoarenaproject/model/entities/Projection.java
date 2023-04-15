package com.example.kinoarenaproject.model.entities;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity(name = "projections")
public class Projection {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "start_time")
    private LocalTime startTime;
    @Column(name = "date")
    private LocalDate date;
  /*  @Column(name = "date")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private Date date; */
    //TODO HAll
 /*   @ManyToOne
    @JoinColumn(name = "hall_id")
    private Hall hallId; */
    @ManyToOne
    @JoinColumn(name = "movie_id")
    private Movie movieId;
}
