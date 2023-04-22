package com.example.kinoarenaproject.service;

import com.example.kinoarenaproject.model.DTOs.*;
import com.example.kinoarenaproject.model.entities.*;
import com.example.kinoarenaproject.model.exceptions.BadRequestException;
import com.example.kinoarenaproject.model.exceptions.NotFoundException;
import com.example.kinoarenaproject.model.exceptions.UnauthorizedException;
import com.example.kinoarenaproject.model.repositories.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public ProjectionDTO add(AddProjectionDTO addProjection, int userId) {
        if (!admin(userId)) {
            throw new UnauthorizedException("Unauthorized role");
        }
        Hall hall = checkOptionalIsPresent(hallRepository.findById(addProjection.getHallId()), "non-existent hall");
        Movie movie = checkOptionalIsPresent(movieRepository.findById(addProjection.getMovieId()), "non-existent movie");

        if (addProjection.getDate() == null ||
                addProjection.getStartTime() == null ||
                addProjection.getPrice() == 0.0) {
            throw new BadRequestException("Incomplete data!");
        }

        Projection projection = mapper.map(addProjection, Projection.class);
        projection.setMovie(movie);
        projection.setHall(hall);

        projectionRepository.save(projection);
        return mapper.map(projection, ProjectionDTO.class);
    }

    public ProjectionDTO edit(EditProjectionDTO projectionDTO, int id, int userId) {
        if (!admin(userId)) {
            throw new UnauthorizedException("Unauthorized role");
        }
        Projection projection = checkOptionalIsPresent(projectionRepository.findById(id), "non-existent projection");

        Optional<Movie> optMovie = movieRepository.findById(projectionDTO.getMovieId());
        if (optMovie.isPresent()) {
            Movie movie = optMovie.get();
            projection.setMovie(movie);
        }
        Optional<Hall> optHall = hallRepository.findById(projectionDTO.getHallId());
        if (optHall.isPresent()) {
            Hall hall = optHall.get();
            projection.setHall(hall);
        }
        setIfNotNull(projectionDTO.getStartTime(), startTime -> projection.setStartTime(startTime));
        setIfNotNull(projectionDTO.getDate(), date -> projection.setDate(date));
        setIfNotNull(projectionDTO.getDate(), price -> projection.setDate(price));

        projectionRepository.save(projection);
        return mapper.map(projection, ProjectionDTO.class);
    }

    public ProjectionDTO remove(int id, int userId) {
        if (!admin(userId)) {
            throw new UnauthorizedException("Unauthorized role");
        }
        Projection projection = checkOptionalIsPresent(projectionRepository.findById(id), "non-existent projection");
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

    public List<FilterResponseDTO> filterByCinema(int cinemaId) {
        return projectionRepository.getProjectionsByCinema(cinemaId).stream()
                .map(p -> new FilterResponseDTO(p.getMovie().getTitle(),
                        p.getHall().getCinema().getName(), p.getDate(), p.getStartTime()))
                .collect(Collectors.toList());
    }

    public List<FilterResponseDTO> filterByMovie(int movieId) {
        return projectionRepository.findByMovieId(movieId).stream()
                .map(p -> new FilterResponseDTO(p.getMovie().getTitle(),
                        p.getHall().getCinema().getName(), p.getDate(), p.getStartTime()))
                .collect(Collectors.toList());
    }

    public List<FilterResponseDTO> filterByCinemaAndMovie(int cinemaId, int movieId) {
        return projectionRepository.getProjectionsByCinemaAndMovie(cinemaId, movieId).stream()
                .map(p -> new FilterResponseDTO(p.getMovie().getTitle(),
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

        List<Ticket> tickets = ticketRepository.findAll().stream().filter(t -> t.getProjection().getId() == projectionId).collect(Collectors.toList());

        int countTickets = 0;
        boolean[][] isTaken = new boolean[r][c];
        for (int i = 0; i < tickets.size(); i++) {
            Ticket ticket = tickets.get(i);
            int row = ticket.getRowNumber();
            int col = ticket.getColNumber();
            isTaken[row][col] = true;
            countTickets += 1;
        }
        projectionAvailableSeatsDTO.setIsTaken(isTaken);
        projectionAvailableSeatsDTO.setCountTakenTickets(countTickets);
        return projectionAvailableSeatsDTO;
    }
}
