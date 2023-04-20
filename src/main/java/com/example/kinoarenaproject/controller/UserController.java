package com.example.kinoarenaproject.controller;

import com.example.kinoarenaproject.model.DTOs.*;
import com.example.kinoarenaproject.model.exceptions.UnauthorizedException;
import com.example.kinoarenaproject.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController extends AbstractController {
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
    public UserWithoutPasswordDTO changePassword(@RequestBody ChangePassDTO changePassData, HttpSession session){
       boolean logged=(boolean) session.getAttribute(Constants.LOGGED);
        if(!logged){
            throw new UnauthorizedException("You have to login");
        }
        int id=loggedId(session);
        return userService.changePassword(changePassData,id);
    }

    @PostMapping("/users/logout")
    public ResponseEntity<String> logout(LogoutDTO logoutData , HttpSession session) {

        boolean logged = (boolean) session.getAttribute(Constants.LOGGED);
        if (!logged) {
            throw new UnauthorizedException("You have to login");
        }
        session.invalidate();
        return ResponseEntity.ok("Logged out successfully");
    }


    @PutMapping("/users/edit")
    public UserWithoutPasswordDTO editProfile(@RequestBody EditProfileDTO editProfileData,HttpSession session){
        boolean logged = (boolean) session.getAttribute(Constants.LOGGED);
        if (!logged) {
            throw new UnauthorizedException("You have to login");
        }
        int id=loggedId(session);
        UserWithoutPasswordDTO u=userService.editProfile(editProfileData,id);
        return u;
    }
    @DeleteMapping("/users/{id}")
    public UserWithoutPasswordDTO delete(@PathVariable int id, HttpSession session){
        int adminId=loggedId(session);
        return  userService.delete(adminId,id);
    }
    @GetMapping("/confirm")
    public String confirmEmail(@RequestParam("token") String token){
        if(userService.confirmEmail(token)){
            return "Email confirmed";
        }else {
            return "Invalid confirmation";
        }
    }


}
