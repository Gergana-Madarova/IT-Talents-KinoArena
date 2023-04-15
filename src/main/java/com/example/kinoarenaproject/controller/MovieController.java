package com.example.kinoarenaproject.controller;

import com.example.kinoarenaproject.serice.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MovieController extends AbstractController{
    @Autowired
    private MovieService movieService;

}
