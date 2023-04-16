package com.example.kinoarenaproject.serice;

import com.example.kinoarenaproject.controller.Constants;
import com.example.kinoarenaproject.model.entities.User;
import com.example.kinoarenaproject.model.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

@org.springframework.stereotype.Service
public abstract class Service {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper mapper;
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
}
