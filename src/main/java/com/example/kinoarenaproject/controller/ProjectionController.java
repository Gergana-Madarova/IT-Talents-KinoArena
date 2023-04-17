package com.example.kinoarenaproject.controller;

import com.example.kinoarenaproject.model.DTOs.AddProjectionDTO;
import com.example.kinoarenaproject.model.DTOs.EditProjectionDTO;
import com.example.kinoarenaproject.model.DTOs.ProjectionDTO;
import com.example.kinoarenaproject.service.ProjectionService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProjectionController extends AbstractController {
    @Autowired
    private ProjectionService projectionService;

    @PostMapping("/projections")
    public ProjectionDTO add(@RequestBody AddProjectionDTO addData, HttpSession session) {
        int id = loggedId(session);
        ProjectionDTO projection = projectionService.add(addData, id);
        return projection;
    }

    @DeleteMapping("/projections/{id}")
    public ProjectionDTO remove(@PathVariable int id, HttpSession session) {
        int userId = loggedId(session);
        return projectionService.remove(id, userId);
    }

    @PutMapping("/projections/{id}")
    public ProjectionDTO edit(@RequestBody EditProjectionDTO editData, @PathVariable int id, HttpSession session) {
        int userId = loggedId(session);
        ProjectionDTO projection = projectionService.edit(editData, id, userId);
        return projection;
    }




    @GetMapping("/projections/{id}")
    public ProjectionDTO getById(@PathVariable int id){
        return projectionService.getById(id);
    }
}
