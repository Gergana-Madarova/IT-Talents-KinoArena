package com.example.kinoarenaproject.serice;

import com.example.kinoarenaproject.controller.Constants;
import com.example.kinoarenaproject.model.DTOs.*;
import com.example.kinoarenaproject.model.entities.Cinema;
import com.example.kinoarenaproject.model.entities.City;
import com.example.kinoarenaproject.model.entities.User;
import com.example.kinoarenaproject.model.exceptions.NotFoundException;
import com.example.kinoarenaproject.model.exceptions.UnauthorizedException;
import com.example.kinoarenaproject.model.repositories.CinemaRepository;
import com.example.kinoarenaproject.model.repositories.CityRepository;
import com.example.kinoarenaproject.model.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CinemaService extends com.example.kinoarenaproject.serice.Service {
    @Autowired
    CinemaRepository cinemaRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    private ModelMapper mapper;
    @Autowired
    CityRepository cityRepository;

    public CinemaDTO add(AddCinemaDTO addCinema, int id) {
        Cinema cin=null;
        try {
            User u = userById(id);
            if (!u.getRole_name().equals(Constants.ADMIN)) {
                throw new UnauthorizedException("Unauthorized role");
            }
             cin = mapper.map(addCinema, Cinema.class);

            Optional<City>opt=cityRepository.findById(addCinema.getCity_id());
            if(!opt.isPresent()){
                throw new NotFoundException("City not found");
            }
           City city= opt.get();
           cin.setCity(city);

            cinemaRepository.save(cin);
        }catch (RuntimeException r){
            r.printStackTrace();
            System.out.println(r.getMessage());
        }
        return mapper.map(cin, CinemaDTO.class);
    }

    public CinemaDTO edit(CinemaDTO cinemaDto, int id,int userId) {
        User u = userById(userId);
        if (!u.getRole_name().equals(Constants.ADMIN)) {
            throw new UnauthorizedException("Unauthorized role");
        }
        Optional<Cinema> opt = cinemaRepository.findById(id);
        if (!opt.isPresent()) {
            System.out.println("_____________________________");
            throw new UnauthorizedException("Wrong credentials");
        }
        Cinema c = opt.get();
        c.setName(cinemaDto.getName());
        c.setAddress(cinemaDto.getAddress());
        c.setPhone_number(cinemaDto.getPhone_number());

//        c=mapper.map(cinemaDto,Cinema.class);
        cinemaRepository.save(c);
        return   mapper.map(c,CinemaDTO.class);

    }
    public CinemaDTO remove(int id, int userId) {
        User u = userById(userId);
        if (!u.getRole_name().equals(Constants.ADMIN)) {
            throw new UnauthorizedException("Unauthorized role");
        }
        Optional <Cinema>opt=cinemaRepository.findById(id);
        if(!opt.isPresent()){
            throw new NotFoundException("Cinema not found");
        }
        Cinema c=opt.get();
        cinemaRepository.delete(c);
        CinemaDTO cDto=mapper.map(c,CinemaDTO.class);
        return  cDto;
    }

    public HashSet<CinemaDTO> getAll() {
        HashSet<Cinema>cinemas=new HashSet<>();
        cinemas.addAll(cinemaRepository.findAll());
        HashSet<CinemaDTO>cinemaDTOS=new HashSet<>();
        for (Cinema c:cinemas){

            cinemaDTOS.add( mapper.map(c,CinemaDTO.class));
        }
        return cinemaDTOS;
    }

    public CinemaDTO getById(int id) {
        Optional<Cinema> opt = cinemaRepository.findById(id);
            if (opt.isPresent()) {
                Cinema u = opt.get();
                return    mapper.map(u, CinemaDTO.class);

            } else {
                throw new NotFoundException("Cinema not found");
            }
    }


    public List<AddCinemaDTO>filterByCity(int cityId) {
        List<Cinema>cinemas=new ArrayList<>();
        City city=cityRepository.findById(cityId).get();
        cinemas.addAll(cinemaRepository.findByCity(city));
//        List<AddCinemaDTO>cinemaDTOS=new ArrayList<>();
//        for (Cinema c:cinemas){
//            cinemaDTOS.add( mapper.map(c,AddCinemaDTO.class));
//        }
//        return cinemaDTOS;
        return cinemas.stream()
                .map(cinema -> mapper.map(cinema, AddCinemaDTO.class))
                .peek(addCinemaDTO -> addCinemaDTO.setCity_id(cityId))
                .collect(Collectors.toList());
    }
}

