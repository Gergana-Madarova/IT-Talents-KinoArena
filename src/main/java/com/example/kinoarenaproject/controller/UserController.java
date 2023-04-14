package com.example.kinoarenaproject.controller;

import com.example.kinoarenaproject.model.DTOs.*;
import com.example.kinoarenaproject.model.entities.Constants;
import com.example.kinoarenaproject.model.exceptions.UnauthorizedException;
import com.example.kinoarenaproject.serice.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    private UserService userService;
    @PostMapping("/users/login")
    public UserWithoutPasswordDTO login(@RequestBody LoginDTO loginData,HttpSession session){
        UserWithoutPasswordDTO u=userService.login(loginData);
        session.setAttribute(Constants.LOGGED,true);
        session.setAttribute(Constants.LOGGED_ID,u.getId());
        return u;
    }
    @PostMapping("/users")
    public UserWithoutPasswordDTO register(@RequestBody RegisterDTO registerData){
        return userService.register(registerData);
    }
    @GetMapping("/users/{id}")
    public UserWithoutPasswordDTO getById(@PathVariable int id){
    return userService.getById(id);
    }

    @PutMapping("/users")
    public UserWithoutPasswordDTO changePassword(@RequestBody ChangePassDTO changePassData, HttpSession s){
       boolean logged=(boolean) s.getAttribute(Constants.LOGGED);
        if(logged){
            return userService.changePassword(changePassData);
        }
        throw new UnauthorizedException("You have to login");
    }
    @PostMapping("/users/logout")
//    public ResponseEntity<String> logout(LogoutDTO logoutData , HttpSession session) {
        //todo
//        boolean logged = (boolean) session.getAttribute(Constants.LOGGED);
//        if (!logged) {
//            throw new UnauthorizedException("You have to login");
//        }
//        session.invalidate();
//        return ResponseEntity.ok("Logged out successfully");
//    }



    @PutMapping("/users/edit")
    public UserWithoutPasswordDTO editProfile(@RequestBody RegisterDTO editProfileData,HttpSession session){
        boolean logged = (boolean) session.getAttribute(Constants.LOGGED);
        if (!logged) {
            throw new UnauthorizedException("You have to login");
        }
        UserWithoutPasswordDTO u=userService.editProfile(editProfileData);
        return u;
    }



}
