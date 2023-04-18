package com.example.kinoarenaproject.service;

import com.example.kinoarenaproject.controller.Constants;
import com.example.kinoarenaproject.model.DTOs.AddCinemaDTO;
import com.example.kinoarenaproject.model.DTOs.AddProjectionDTO;
//import com.example.kinoarenaproject.model.DTOs.EditProjectionDTO;
import com.example.kinoarenaproject.model.DTOs.EditProjectionDTO;
import com.example.kinoarenaproject.model.DTOs.ProjectionDTO;
import com.example.kinoarenaproject.model.entities.*;
import com.example.kinoarenaproject.model.exceptions.NotFoundException;
import com.example.kinoarenaproject.model.exceptions.UnauthorizedException;
import com.example.kinoarenaproject.model.repositories.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProjectionService extends com.example.kinoarenaproject.service.Service {
    @Autowired
    private ProjectionRepository projectionRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    MovieRepository movieRepository;
    @Autowired
    HallRepository hallRepository;
    @Autowired
    private ModelMapper mapper;
    @Autowired
    CinemaRepository cinemaRepository;

    public ProjectionDTO add(AddProjectionDTO addProjection, int id) {
        Projection projection = null;
        try {
            User u = userById(id);
            if (!u.getRole_name().equals(Constants.ADMIN)) {
                throw new UnauthorizedException("Unauthorized role");
            }
            projection = mapper.map(addProjection, Projection.class);

            Optional<Movie> optMovie = movieRepository.findById(addProjection.getMovieId());
            if (!optMovie.isPresent()) {
                throw new NotFoundException("Movie not found");
            }
            Movie movie = optMovie.get();
            projection.setMovieId(movie);

            Optional<Hall> optHall = hallRepository.findById(addProjection.getHallId());
            if (!optHall.isPresent()) {
                throw new NotFoundException("Movie not found");
            }
            Hall hall = optHall.get();
            projection.setHallId(hall);

            projectionRepository.save(projection);
        } catch (RuntimeException r) {
            r.printStackTrace();
            System.out.println(r.getMessage());
        }
        return mapper.map(projection, ProjectionDTO.class);
    }

    public ProjectionDTO edit(EditProjectionDTO projectionDTO, int id, int userId) {
        User u = userById(userId);
        if (!u.getRole_name().equals(Constants.ADMIN)) {
            throw new UnauthorizedException("Unauthorized role");
        }
        Optional<Projection> opt = projectionRepository.findById(id);
        if (!opt.isPresent()) {
            throw new UnauthorizedException("non-existent projection");
        }
        Projection projection = opt.get();
        projection.setStartTime(projectionDTO.getStartTime());
        projection.setDate(projectionDTO.getDate());
        Optional<Movie> optMovie = movieRepository.findById(projectionDTO.getMovieId());
        Movie movie = optMovie.get();
        projection.setMovieId(movie);
        Optional<Hall> optHall = hallRepository.findById(projectionDTO.getHallId());
        Hall hall = optHall.get();
        projection.setHallId(hall);

        projectionRepository.save(projection);
        return mapper.map(projection, ProjectionDTO.class);
    }

    public ProjectionDTO remove(int id, int userId) {
        User u = userById(userId);
        if (!u.getRole_name().equals(Constants.ADMIN)) {
            throw new UnauthorizedException("Unauthorized role");
        }
        Optional<Projection> opt = projectionRepository.findById(id);
        if (!opt.isPresent()) {
            throw new NotFoundException("Projection not found");
        }

        Projection projection = opt.get();
        projectionRepository.delete(projection);

        ProjectionDTO projectionDTO = mapper.map(projection, ProjectionDTO.class);
        return projectionDTO;
    }

    public ProjectionDTO getById(int id) {
        Optional<Projection> opt = projectionRepository.findById(id);
        if (opt.isPresent()) {
            return mapper.map(opt.get(), ProjectionDTO.class);
        } else {
            throw new NotFoundException("Projection is not found");
        }
    }





    public List<AddProjectionDTO> filterByMovie(int movieId) {
        List<Movie> movies = new ArrayList<>();
        Movie movie = movieRepository.findById(movieId).get();
        movies.addAll(projectionRepository.findByMovieId(movieId));
        return movies.stream()
                .map(projection -> mapper.map(projection, AddProjectionDTO.class))
                .peek(addProjectionDTO -> addProjectionDTO.setMovieId(movieId))
                .collect(Collectors.toList());
    }


}
