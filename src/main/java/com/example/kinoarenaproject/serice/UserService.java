package com.example.kinoarenaproject.serice;

import com.example.kinoarenaproject.controller.ValidationUtils;
import com.example.kinoarenaproject.model.DTOs.*;
import com.example.kinoarenaproject.model.entities.User;
import com.example.kinoarenaproject.model.exceptions.BadRequestException;
import com.example.kinoarenaproject.model.exceptions.NotFoundException;
import com.example.kinoarenaproject.model.exceptions.UnauthorizedException;
import com.example.kinoarenaproject.model.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper mapper;
    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    public UserWithoutPasswordDTO login(LoginDTO loginData) {

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
        if (!registerData.getPassword().equals((registerData).getConfirmPassword())) {
            throw new BadRequestException("Password mismatched");
        }
        if (ValidationUtils.isValidPassword(registerData.getPassword()) &&
                ValidationUtils.isValidEmail(registerData.getEmail())) {
            if (userRepository.existsByEmail(registerData.getEmail())) {
                throw new BadRequestException("Email already exist");
            }
        } else {
            throw new BadRequestException(("Inadequate input for password  " +
                    "At least one upper case, one lower case,one digit,one special character minimum eight characters , max 20"));
        }
        User u = mapper.map(registerData, User.class);
        u.setPassword(passwordEncoder.encode(u.getPassword()));
        userRepository.save(u);
        return mapper.map(u, UserWithoutPasswordDTO.class);

    }

    public UserWithoutPasswordDTO getById(int id) {
        Optional<User> opt = userRepository.findById(id);
        if (opt.isPresent()) {
            return mapper.map(opt.get(), UserWithoutPasswordDTO.class);
        } else {
            throw new NotFoundException("User not found");
        }
    }

    public UserWithoutPasswordDTO changePassword(ChangePassDTO changePassData, int id) {
        Optional<User> opt = userRepository.findById(id);
        if (!opt.isPresent()) {
            throw new UnauthorizedException("Wrong credentials");
        }
        User u = opt.get();
        u.setPassword(passwordEncoder.encode(changePassData.getNewPassword()));
        userRepository.save(u);
        return mapper.map(u, UserWithoutPasswordDTO.class);
    }

    public UserWithoutPasswordDTO editProfile(EditProfileDTO editProfileData, int id) {
        Optional<User> opt = userRepository.findById(id);
        if (!opt.isPresent()) {
            throw new UnauthorizedException("Wrong credentials");
        }
        User u = opt.get();
//               u= mapper.map(editProfileData,User.class);
        u.setPhone_number(editProfileData.getPhone_number());
        u.setEmail(editProfileData.getEmail());
        u.setBirth_date(editProfileData.getBirth_date());
        u.setFirst_name(editProfileData.getFirst_name());
        u.setLast_name(editProfileData.getLast_name());
        u.setGender(editProfileData.getGender());
        u.setCity_id(editProfileData.getCity_id());

//        u.setPassword(passwordEncoder.encode(u.getPassword()));
        userRepository.save(u);
        return mapper.map(u, UserWithoutPasswordDTO.class);
    }
}








