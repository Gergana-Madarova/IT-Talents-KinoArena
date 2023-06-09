package com.example.kinoarenaproject.service;

import com.example.kinoarenaproject.controller.Constants;
import com.example.kinoarenaproject.model.entities.User;
import com.example.kinoarenaproject.model.exceptions.UnauthorizedException;
import com.example.kinoarenaproject.model.repositories.CityRepository;
import com.example.kinoarenaproject.model.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;
import java.util.function.Consumer;

@org.springframework.stereotype.Service
public abstract class Service {
    @Autowired
    public UserRepository userRepository;
    @Autowired
    CityRepository cityRepository;
    @Autowired
    public ModelMapper mapper;
    @Autowired
    BCryptPasswordEncoder passwordEncoder;
    public User userById(int id){
        Optional<User>opt=userRepository.findById(id);
        User u=opt.get();
        return u;
    }

    public boolean admin(int userId){
       User u=userById(userId);
      return u.getRole_name().equals(Constants.ADMIN) ;
    }

    public  <T> T checkOptionalIsPresent(Optional<T> optional, String errorMessage) {
        if (!optional.isPresent()) {
            throw new UnauthorizedException(errorMessage);
        }
        return optional.get();
    }

    public <T> void setIfNotNull(T value, Consumer<T> setter) {
        if (value != null) {
            setter.accept(value);
        }
    }

}
