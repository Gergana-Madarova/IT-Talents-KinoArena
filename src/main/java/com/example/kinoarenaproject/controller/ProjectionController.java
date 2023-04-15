package com.example.kinoarenaproject.controller;

import com.example.kinoarenaproject.serice.ProjectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProjectionController extends AbstractController{
    @Autowired
    private ProjectionService projectionService;


}
