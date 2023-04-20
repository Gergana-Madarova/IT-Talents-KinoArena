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

    public MovieInfoDTO add(AddMovieDTO addData, int id) {
        Movie movie = null;
        try {
            User u = userById(id);
            if (!u.getRole_name().equals(Constants.ADMIN)) {
                throw new UnauthorizedException("Unauthorized role");
            }
            movie = mapper.map(addData, Movie.class);

            Optional<Category> optCategory = categoryRepository.findById(addData.getCategoryId());
            if (!optCategory.isPresent()) {
                throw new NotFoundException("Category not found");
            }
            Category category = optCategory.get();
            movie.setCategory(category);

            Optional<Genre> optGenre = genreRepository.findById(addData.getGenreId());
            if (!optGenre.isPresent()) {
                throw new NotFoundException("Movie not found");
            }
            Genre genre = optGenre.get();
            movie.setGenre(genre);

            movieRepository.save(movie);
        } catch (RuntimeException r) {
            r.printStackTrace();
            System.out.println(r.getMessage());
        }
        return mapper.map(movie, MovieInfoDTO.class);
    }

    public MovieInfoDTO edit(EditMovieDTO movieDTO, int id, int userId) {
        User u = userById(userId);
        if (!u.getRole_name().equals(Constants.ADMIN)) {
            throw new UnauthorizedException("Unauthorized role");
        }
        Optional<Movie> opt = movieRepository.findById(id);
        if (!opt.isPresent()) {
            throw new UnauthorizedException("non-existent movie");
        }
        Movie movie = opt.get();
        movie.setTitle(movieDTO.getTitle());
        movie.setDescription(movieDTO.getDescription());
        movie.setDuration(movieDTO.getDuration());
        movie.setPremiere(movieDTO.getPremiere());
        movie.setDirector(movieDTO.getDirector());
        movie.setCast(movieDTO.getCast());

        Optional<Category> optionalCategory = categoryRepository.findById(movieDTO.getCategory());
        Category category = optionalCategory.get();
        movie.setCategory(category);

        Optional<Genre> optionalGenre = genreRepository.findById(movieDTO.getGenre());
        Genre genre = optionalGenre.get();
        movie.setGenre(genre);

        movieRepository.save(movie);
        return mapper.map(movie, MovieInfoDTO.class);
    }

    public MovieInfoDTO remove(int id, int userId) {
        User u = userById(userId);
        if (!u.getRole_name().equals(Constants.ADMIN)) {
            throw new UnauthorizedException("Unauthorized role");
        }
        Optional<Movie> opt = movieRepository.findById(id);
        if (!opt.isPresent()) {
            throw new NotFoundException("Movie not found");
        }
        Movie movie = opt.get();
        movieRepository.delete(movie);
        MovieInfoDTO movieInfoDTO = mapper.map(movie, MovieInfoDTO.class);
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

    public List<MovieDTO> getAll() {
        return movieRepository.findAll()
                .stream()
                .map(m -> mapper.map(m, MovieDTO.class))
                .collect(Collectors.toList());
    }

    public MovieInfoDTO getInfo(int id) {
        Optional<Movie> opt = movieRepository.findById(id);
        if (opt.isPresent()) {
            return mapper.map(opt.get(), MovieInfoDTO.class);
        } else {
            throw new NotFoundException("Movie with this id is not found");
        }
    }

    public List<AddMovieDTO> filterByGenre(int genreId) {
        Optional<Genre> genre = genreRepository.findById(genreId);
        if (genre.isPresent()) {
            mapper.map(genre.get(), Genre.class);
            List<Movie> movies = new ArrayList<>();
            movies.addAll(movieRepository.findByGenre(genre));
            return movies.stream()
                    .map(m -> mapper.map(m, AddMovieDTO.class))
                    .peek(addMovieDTO -> addMovieDTO.setGenreId(genreId))
                    .collect(Collectors.toList());
        } else {
            throw new NotFoundException("Not found genre");
        }
    }
}
