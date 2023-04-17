package com.example.kinoarenaproject.service;

import com.example.kinoarenaproject.controller.Constants;
import com.example.kinoarenaproject.model.DTOs.*;
import com.example.kinoarenaproject.model.entities.*;
import com.example.kinoarenaproject.model.exceptions.NotFoundException;
import com.example.kinoarenaproject.model.exceptions.UnauthorizedException;
import com.example.kinoarenaproject.model.repositories.CategoryRepository;
import com.example.kinoarenaproject.model.repositories.GenreRepository;
import com.example.kinoarenaproject.model.repositories.MovieRepository;
import com.example.kinoarenaproject.model.repositories.UserRepository;
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
        Optional <Movie>opt=movieRepository.findById(id);
        if(!opt.isPresent()){
            throw new NotFoundException("Movie not found");
        }

        Movie movie = opt.get();
        movieRepository.delete(movie);

        MovieInfoDTO movieInfoDTO=mapper.map(movie, MovieInfoDTO.class);
        return  movieInfoDTO;
    }

    public MovieDTO getById(int id) {
        Optional<Movie> opt = movieRepository.findById(id);
        if (opt.isPresent()) {
            return mapper.map(opt.get(), MovieDTO.class);
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

    /*
            List<Movie>movies=new ArrayList<>();
        movies.addAll(movieRepository.findAll());
        movies.stream()
                .map(m -> mapper.map(m, MovieInfoDTO.class))

        public HashSet<CinemaDTO> getAll() {
        HashSet<Cinema>cinemas=new HashSet<>();
        cinemas.addAll(cinemaRepository.findAll());
        HashSet<CinemaDTO>cinemaDTOS=new HashSet<>();
        for (Cinema c:cinemas){

            cinemaDTOS.add( mapper.map(c,CinemaDTO.class));
        }
        return cinemaDTOS;
    }
     */

    // TODO за add edit delete  трябва да проверя първо дали е логнат като админ - в контролера?

 /*   public List<MovieDTO>filterByCinema(int cinemaId) {
        List<Movie> movies = new ArrayList<>();
        movies.addAll(movieRepository.findByCinema(cinemaId));
        List<MovieDTO> movieDTOS = new ArrayList<>();
        for (Movie m : movies) {
            movieDTOS.add(mapper.map(m, MovieDTO.class));
        }
        return movieDTOS;
    }

  */

}
