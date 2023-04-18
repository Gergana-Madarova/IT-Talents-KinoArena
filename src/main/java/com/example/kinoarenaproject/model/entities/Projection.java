package com.example.kinoarenaproject.model.entities;

import com.example.kinoarenaproject.model.DTOs.HallDTO;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity(name = "projections")
@Table
@Setter
@Getter
public class Projection {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "start_time")
    private LocalTime startTime;
    @Column(name = "date")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate date;
    //TODO въпрос: една прожекция също може да я има в много зали?
    @ManyToOne
    @JoinColumn(name = "hall_id")
    private Hall hallId;
    @ManyToOne
    @JoinColumn(name = "movie_id")
    private Movie movieId;

}
