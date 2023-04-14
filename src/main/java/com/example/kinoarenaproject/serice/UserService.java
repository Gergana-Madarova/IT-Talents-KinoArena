package com.example.kinoarenaproject.serice;

import com.example.kinoarenaproject.controller.ValidationUtils;
import com.example.kinoarenaproject.model.DTOs.ChangePassDTO;
import com.example.kinoarenaproject.model.DTOs.LoginDTO;
import com.example.kinoarenaproject.model.DTOs.RegisterDTO;
import com.example.kinoarenaproject.model.DTOs.UserWithoutPasswordDTO;
import com.example.kinoarenaproject.model.entities.User;
import com.example.kinoarenaproject.model.exceptions.BadRequestException;
import com.example.kinoarenaproject.model.exceptions.NotFoundException;
import com.example.kinoarenaproject.model.exceptions.UnauthorizedException;
import com.example.kinoarenaproject.model.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
        @Autowired
        private UserRepository userRepository;
        @Autowired
        private ModelMapper mapper;
        @Autowired
        BCryptPasswordEncoder passwordEncoder;

    public UserWithoutPasswordDTO  login(LoginDTO loginData) {

            Optional<User> opt = userRepository.findByEmail(loginData.getEmail());
            if (!opt.isPresent()) {
                throw new UnauthorizedException("Wrong credentials");
            }
            if (!userRepository.existsByEmail(loginData.getEmail())) {
                throw new UnauthorizedException("Wrong credentials");
            }


            User u = opt.get();
            if (passwordEncoder.matches(loginData.getPassword(), u.getPassword())) {
                return mapper.map(u, UserWithoutPasswordDTO.class);
            } else {
                throw new UnauthorizedException("Wrong credentials");
            }

    }

    public UserWithoutPasswordDTO register(RegisterDTO registerData) {
        if(!registerData.getPassword().equals((registerData).getConfirmPassword())){
            throw new BadRequestException("Password mismached");
        }
        if (ValidationUtils.isValidPassword(registerData.getPassword()) &&
                ValidationUtils.isValidEmail(registerData.getEmail() )){
            if(userRepository.existsByEmail(registerData.getEmail())){
                throw new BadRequestException("Email already exist");
            }
        }

            User u = mapper.map(registerData, User.class);
            u.setPassword(passwordEncoder.encode(u.getPassword()));
            userRepository.save(u);
            return mapper.map(u, UserWithoutPasswordDTO.class);

    }

    public UserWithoutPasswordDTO getById(int id) {
        Optional opt=userRepository.findById(id);
        if(opt.isPresent()){
            return mapper.map(opt.get(),UserWithoutPasswordDTO.class);
        }
        throw new NotFoundException("User not found");
    }

    public UserWithoutPasswordDTO changePassword(ChangePassDTO changePassData) {

return null;
    }

    public UserWithoutPasswordDTO editProfile(RegisterDTO editProfileData) {

return null;//todo

    }

    public void logout(LoginDTO logoutData) {
// to do

    }
}
