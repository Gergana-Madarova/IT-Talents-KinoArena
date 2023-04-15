package com.example.kinoarenaproject.serice;

import com.example.kinoarenaproject.model.repositories.ProjectionRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectionService {
    @Autowired
    private ProjectionRepository projectionRepository;
    @Autowired
    private ModelMapper mapper;
}
