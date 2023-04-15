package com.example.kinoarenaproject.serice;

import com.example.kinoarenaproject.controller.Constants;
import com.example.kinoarenaproject.model.DTOs.AddCinemaDTO;
import com.example.kinoarenaproject.model.DTOs.CinemaWithoutCity;
import com.example.kinoarenaproject.model.DTOs.EditCinemaDTO;
import com.example.kinoarenaproject.model.entities.Cinema;
import com.example.kinoarenaproject.model.entities.User;
import com.example.kinoarenaproject.model.exceptions.NotFoundException;
import com.example.kinoarenaproject.model.exceptions.UnauthorizedException;
import com.example.kinoarenaproject.model.repositories.CinemaRepository;
import com.example.kinoarenaproject.model.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;

@Service
public class CinemaService extends com.example.kinoarenaproject.serice.Service {
    @Autowired
    CinemaRepository cinemaRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    private ModelMapper mapper;
    public CinemaWithoutCity add(AddCinemaDTO addCinema, int id) {
//
        User u = findById(id);
        if (!u.getRole_name().equals(Constants.ADMIN)) {
            throw new UnauthorizedException("Unauthorized role");
        }
        Cinema cinema = mapper.map(addCinema, Cinema.class);
        cinemaRepository.save(cinema);

        return mapper.map(cinema, CinemaWithoutCity.class);
    }

    public CinemaWithoutCity edit(EditCinemaDTO editData, int id) {
        return null;
    }

    public Cinema remove(int id, int userId) {
        User u = findById(id);
        if (!u.getRole_name().equals(Constants.ADMIN)) {
            throw new UnauthorizedException("Unauthorized role");
        }
        Optional <Cinema>opt=cinemaRepository.findById(id);
        if(!opt.isPresent()){
            throw new NotFoundException("Cinema not found");
        }
        Cinema cinema=opt.get();
        cinemaRepository.delete(cinema);
        return  cinema;
    }

    public HashSet<Cinema> getAll() {
        HashSet<Cinema>cinemas=new HashSet<>();
        cinemas.addAll(cinemaRepository.findAll());
        return cinemas;
    }

    public CinemaWithoutCity getById(int id) {
        return null;
    }
}
