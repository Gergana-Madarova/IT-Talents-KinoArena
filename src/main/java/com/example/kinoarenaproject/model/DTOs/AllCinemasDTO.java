package com.example.kinoarenaproject.model.DTOs;

import com.example.kinoarenaproject.model.entities.Cinema;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AllCinemasDTO {
    private HashSet<Cinema>cinemaSet=new HashSet<>();
    private int id;
    private String name;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AllCinemasDTO that = (AllCinemasDTO) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    private String adress;

}
