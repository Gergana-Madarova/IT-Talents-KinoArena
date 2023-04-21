package com.example.kinoarenaproject.service;

import com.example.kinoarenaproject.model.DTOs.*;
import com.example.kinoarenaproject.model.entities.*;
import com.example.kinoarenaproject.model.exceptions.BadRequestException;
import com.example.kinoarenaproject.model.exceptions.NotFoundException;
import com.example.kinoarenaproject.model.exceptions.UnauthorizedException;
import com.example.kinoarenaproject.model.repositories.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MovieService extends com.example.kinoarenaproject.service.Service {
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    GenreRepository genreRepository;
    @Autowired
    HallRepository hallRepository;
    @Autowired
    ProjectionRepository projectionRepository;
    @Autowired
    private ModelMapper mapper;

    public MovieInfoDTO add(AddMovieDTO movieDTO, int userId) {
        if (!admin(userId)) {
            throw new UnauthorizedException("Unauthorized role");
        }
        Category category = checkOptionalIsPresent(categoryRepository.findById(movieDTO.getCategoryId()), "non-existent category");
        Genre genre = checkOptionalIsPresent(genreRepository.findById(movieDTO.getGenreId()), "non-existent genre");

        if (movieDTO.getTitle() == null ||
                movieDTO.getDescription() == null ||
                movieDTO.getDuration() == 0 ||
                movieDTO.getPremiere() == null) {
            throw new BadRequestException("Incomplete data!");
        }

        Movie movie = mapper.map(movieDTO, Movie.class);
        movie.setCategory(category);
        movie.setGenre(genre);

        movieRepository.save(movie);
        return mapper.map(movie, MovieInfoDTO.class);
    }

    public MovieInfoDTO edit(EditMovieDTO movieDTO, int id, int userId) {
        if (!admin(userId)) {
            throw new UnauthorizedException("Unauthorized role");
        }
        Movie movie = checkOptionalIsPresent(movieRepository.findById(id), "non-existent movie");
        Category category = checkOptionalIsPresent(categoryRepository.findById(movieDTO.getCategory()), "non-existent category");
        Genre genre = checkOptionalIsPresent(genreRepository.findById(movieDTO.getGenre()), "non-existent genre");

        setIfNotNull(movieDTO.getTitle(), title -> movie.setTitle(title));
        setIfNotNull(movieDTO.getDescription(), description -> movie.setDescription(description));
        setIfNotNull(movieDTO.getDuration(), duration -> movie.setDuration(duration));
        setIfNotNull(movieDTO.getPremiere(), premiere -> movie.setPremiere(premiere));
        setIfNotNull(movieDTO.getDirector(), director -> movie.setDirector(director));
        setIfNotNull(movieDTO.getCast(), cast -> movie.setCast(cast));
        setIfNotNull(category, category1 -> movie.setCategory(category1));
        setIfNotNull(genre, genre1 -> movie.setGenre(genre1));

        movieRepository.save(movie);
        return mapper.map(movie, MovieInfoDTO.class);
    }

    @Transactional
    public MovieInfoDTO remove(int id, int userId) {
        if (!admin(userId)) {
            throw new UnauthorizedException("Unauthorized role!");
        }
        Movie movie = checkOptionalIsPresent(movieRepository.findById(id), "non-existent movie");
        List<Projection> projections = projectionRepository.findByMovie(Optional.of(movie));
        for (Projection projection : projections) {
            projectionRepository.delete(projection);
        }
        MovieInfoDTO movieInfoDTO = mapper.map(movie, MovieInfoDTO.class);
        movie.setGenre(null);
        movie.setCategory(null);
        movieRepository.delete(movie);
        return movieInfoDTO;
    }

    public MovieDTO getById(int id) {
        Optional<Movie> opt = movieRepository.findById(id);
        if (opt.isPresent()) {
            Movie movie = opt.get();
            MovieDTO movieDTO = mapper.map(movie, MovieDTO.class);
            return movieDTO;
        } else {
            throw new NotFoundException("Movie with this id is not found");
        }
    }

    public Page<MovieDTO> getAll(Pageable pageable) {
        Page<Movie> moviesPage = movieRepository.findAll(pageable);
        List<MovieDTO> movies = moviesPage.getContent()
                .stream()
                .map(m -> mapper.map(m, MovieDTO.class))
                .collect(Collectors.toList());
        return new PageImpl<>(movies, pageable, moviesPage.getTotalElements());
    }

    public MovieInfoDTO getInfo(int id) {
        Optional<Movie> opt = movieRepository.findById(id);
        if (opt.isPresent()) {
            return mapper.map(opt.get(), MovieInfoDTO.class);
        } else {
            throw new NotFoundException("Movie with this id is not found");
        }
    }

    public List<AddMovieDTO> filterByGenre(int id) {
        Optional<Genre> genre = genreRepository.findById(id);
        if (genre.isPresent()) {
            List<Movie> movies = new ArrayList<>();
            movies.addAll(movieRepository.findByGenre(genre));
            return movies.stream()
                    .map(m -> mapper.map(m, AddMovieDTO.class))
                    .peek(addMovieDTO -> addMovieDTO.setGenreId(id))
                    .collect(Collectors.toList());
        } else {
            throw new NotFoundException("Not found genre!");
        }
    }
}