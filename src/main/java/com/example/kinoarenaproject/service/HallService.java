package com.example.kinoarenaproject.service;

import com.example.kinoarenaproject.controller.Constants;
import com.example.kinoarenaproject.model.DTOs.AddHallDTO;
import com.example.kinoarenaproject.model.DTOs.CinemaDTO;
import com.example.kinoarenaproject.model.DTOs.HallDTO;
import com.example.kinoarenaproject.model.entities.Cinema;
import com.example.kinoarenaproject.model.entities.City;
import com.example.kinoarenaproject.model.entities.Hall;
import com.example.kinoarenaproject.model.entities.User;
import com.example.kinoarenaproject.model.exceptions.NotFoundException;
import com.example.kinoarenaproject.model.exceptions.UnauthorizedException;
import com.example.kinoarenaproject.model.repositories.CinemaRepository;
import com.example.kinoarenaproject.model.repositories.HallRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class HallService extends com.example.kinoarenaproject.service.Service {
    @Autowired
    HallRepository hallRepository;
    @Autowired
    ModelMapper mapper;
    @Autowired
    CinemaRepository cinemaRepository;

    public HallDTO add(AddHallDTO addData, int id) {

        Hall hall=null;
            try {
                User u = userById(id);
                if(! admin(id)){
                    throw new UnauthorizedException("Unauthorized role");
                }

//          Optional<City> opt=cityRepository.findById(addCinema.getCity_id());
                Optional<Cinema>opt=cinemaRepository.findById(addData.getCinemaId());
                if(!opt.isPresent()){
                    throw new NotFoundException("Cinema not found");
                }
//                City city= opt.get();
//                cin.setCity(city);
                Cinema cinema= opt.get();
                hall = mapper.map(addData,Hall.class);
                hall.setCinema(cinema);
                hallRepository.save(hall);

//                cinemaRepository.save(cin);
            }catch (RuntimeException r){
                r.printStackTrace();
                System.out.println(r.getMessage());
            }
            return mapper.map(hall,HallDTO.class);
        }

    public List<HallDTO> filterByCinema(int cinemaId) {
        return null;
    }

    public HallDTO edit(HallDTO editData, int id, int userId) {

        User u = userById(userId);
        if(! admin(userId)){
            throw new UnauthorizedException("Unauthorized role");
        }
        Optional<Hall> opt = hallRepository.findById(id);
        if (!opt.isPresent()) {
            System.out.println("_____________________________");
            throw new UnauthorizedException("Wrong credentials");
        }
        Hall h = opt.get();
        h.setTypeId(editData.getTypeId());
        h.setRows(editData.getRows());
        h.setColumns(editData.getColumns());

//        c=mapper.map(cinemaDto,Cinema.class);
        hallRepository.save(h);
        return   mapper.map(h,HallDTO.class);

    }


    public HallDTO getById(int id) {
        Optional<Hall> opt = hallRepository.findById(id);
        if (opt.isPresent()) {
            Hall h = opt.get();
            return mapper.map(h, HallDTO.class);
        } else {
            throw new NotFoundException("Cinema not found");
        }
    }


//    public List<HallDTO> getSeets() {
//            public List<CinemaDTO>filterByCity(int cityId) {
//                List<Cinema>cinemas=new ArrayList<>();
//
//                cinemas.addAll(cinemaRepository.findByCity(cityId));
//                List<CinemaDTO>cinemaDTOS=new ArrayList<>();
//                for (Cinema c:cinemas){
//                    cinemaDTOS.add( mapper.map(c,CinemaDTO.class));
//                }
//                return cinemaDTOS;
//        }
//    return null;
//
//    }

    public HallDTO remove(int id, int userId) {

        User u = userById(userId);
        if (! admin(userId)) {
            throw new UnauthorizedException("Unauthorized role");
        }
        Optional <Hall>opt=hallRepository.findById(id);
        if(!opt.isPresent()){
            throw new NotFoundException("Cinema not found");
        }
        Hall h=opt.get();
        hallRepository.delete(h);
        HallDTO hallDTO=mapper.map(h,HallDTO.class);
        return  hallDTO;
    }


}
