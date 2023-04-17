package com.example.kinoarenaproject.controller;

import com.example.kinoarenaproject.model.DTOs.*;
import com.example.kinoarenaproject.service.MovieService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
//import jakarta.servlet.http.HttpSession;


@RestController
public class MovieController extends AbstractController {
    @Autowired
    private MovieService movieService;

    @PostMapping("/movies")
    public MovieInfoDTO add(@RequestBody AddMovieDTO addData, HttpSession session) {
        int id = loggedId(session);
        MovieInfoDTO movie = movieService.add(addData, id);
        return movie;
    }

    @DeleteMapping("/movies/{id}")
    public MovieInfoDTO remove(@PathVariable int id, HttpSession session) {
        int userId = loggedId(session);
        return movieService.remove(id, userId);
    }

    @PutMapping("/movies/{id}")
    public MovieInfoDTO edit(@RequestBody EditMovieDTO editData, @PathVariable int id, HttpSession session) {
        int userId = loggedId(session);
        MovieInfoDTO movie = movieService.edit(editData, id, userId);
        return movie;
    }

    @GetMapping("/movies/{id}")
    public MovieDTO getById(@PathVariable int id) {
        return movieService.getById(id);
    }

    @GetMapping("/movies/all")
    public List<MovieDTO> getAll() {
        List<MovieDTO> movies = movieService.getAll();
        return movies;
    }

    @GetMapping("/movies/{id}/info")
    public MovieInfoDTO getInfo(@PathVariable int id) {
        return movieService.getInfo(id);
    }

    @PostMapping("movies/filter")
    public List<MovieDTO> filter(@RequestBody int cinemaId) {
        List<MovieDTO> movieList = movieService.filterByCinema(cinemaId);
        return movieList;
    }
}
