package com.example.kinoarenaproject.service;

import com.example.kinoarenaproject.controller.Constants;
import com.example.kinoarenaproject.model.DTOs.*;
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
    TicketRepository ticketRepository;
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
            projection.setMovie(movie);

            Optional<Hall> optHall = hallRepository.findById(addProjection.getHallId());
            if (!optHall.isPresent()) {
                throw new NotFoundException("Movie not found");
            }
            Hall hall = optHall.get();
            projection.setHall(hall);

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
        projection.setMovie(movie);
        Optional<Hall> optHall = hallRepository.findById(projectionDTO.getHallId());
        Hall hall = optHall.get();
        projection.setHall(hall);

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
        Optional<Movie> movie = movieRepository.findById(movieId);
        if (movie.isPresent()) {
            mapper.map(movie.get(), Movie.class);
            List<Projection> projections = new ArrayList<>();
            projections.addAll(projectionRepository.findByMovie(movie));
            return projections.stream()
                    .map(m -> mapper.map(m, AddProjectionDTO.class))
                    .peek(addProjectionDTO -> addProjectionDTO.setMovieId(movieId))
                    .collect(Collectors.toList());
        } else {
            throw new NotFoundException("Movie with this id is not found");
        }
    }

    public List<ProjectionByCinemaDTO> filterByCinema(int cinemaId) {
        return projectionRepository.getProjectionsByCinema(cinemaId).stream()
                .map(p -> new ProjectionByCinemaDTO(p.getId(), p.getMovie().getTitle(),
                        p.getHall().getCinema().getName(), p.getDate(), p.getStartTime()))
                .collect(Collectors.toList());
    }

    public ProjectionAvailableSeatsDTO getAvailableSeats(int projectionId) {
        ProjectionDTO projectionDTO = getById(projectionId);
        ProjectionAvailableSeatsDTO projectionAvailableSeatsDTO = new ProjectionAvailableSeatsDTO();
        int r = projectionDTO.getHall().getRows();
        int c = projectionDTO.getHall().getColumns();
        projectionAvailableSeatsDTO.setRows(r);
        projectionAvailableSeatsDTO.setCols(c);
        //   projectionAvailableSeatsDTO.setIsTaken(new boolean[r][c]);

        List<Ticket> tickets = ticketRepository.findAll().stream().filter(t -> t.getProjection().getId() == projectionId).collect(Collectors.toList());

        int countTickets = 0;
        boolean[][] isTaken = new boolean[r][c];
        for (int i = 0; i < tickets.size(); i++) {
            Ticket ticket = tickets.get(i);
            int row = ticket.getRowNumber();
            int col = ticket.getColNumber();
            isTaken[row][col] = true;           // Сетваме стойността на true
            countTickets += 1;
        }
        projectionAvailableSeatsDTO.setIsTaken(isTaken);
        projectionAvailableSeatsDTO.setCountTakenTickets(countTickets);
        return projectionAvailableSeatsDTO;
    }
}
