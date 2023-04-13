package com.example.kinoarenaproject.model.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private int id;
     private  String first_name;
    private String last_name;
    private String email;
    private String password;
    private Date birth_date;
    private int city_id;
    private String gender;
    private String phone_number;

    private String role_name;
}
