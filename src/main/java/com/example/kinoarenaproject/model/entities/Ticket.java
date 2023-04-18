package com.example.kinoarenaproject.model.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "tickets")
@Table
@Setter
@Getter
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @Column(name = "is_regular")
    private boolean isRegular;
    @ManyToOne
    @JoinColumn(name = "projection_id")
    private Projection projectionId;
    @Column(name = "row_number")
    private int rowNumber;
    @Column(name = "col_number")
    private int colNumber;
    @Column(name = "price")
    private double price;
    @Column(name = "discount")
    private Double discount;
}
