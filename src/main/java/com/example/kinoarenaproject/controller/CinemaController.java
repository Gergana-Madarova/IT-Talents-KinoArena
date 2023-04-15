package com.example.kinoarenaproject.controller;

import com.example.kinoarenaproject.model.DTOs.*;
import com.example.kinoarenaproject.model.entities.Cinema;
import com.example.kinoarenaproject.serice.CinemaService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;

@RestController
public class CinemaController extends AbstractController{

    @Autowired
    private CinemaService cinemaService;
    @PostMapping("/cinemas")
    public CinemaWithoutCity add(@RequestBody AddCinemaDTO addData, HttpSession session){
        int id=loggedId(session);
       CinemaWithoutCity cinema=cinemaService.add(addData,id);
//        session.setAttribute(Constants.LOGGED,true);
//        session.setAttribute(Constants.LOGGED_ID,u.getId());
        return cinema;
    }
   @PostMapping("cinemas/filter")
    public CinemaWithoutCity filter(@RequestBody int cityId,int projectionId){
        return null;// todo
   }
   @PutMapping("/cinemas/{id}")
   public CinemaWithoutCity edit(@RequestBody EditCinemaDTO editData,HttpSession session){
        int id=loggedId(session);
       CinemaWithoutCity cinema=cinemaService.edit(editData,id);
       return cinema;
   }
   @GetMapping("/cinemas/{id}")
    public CinemaWithoutCity getById(@PathVariable int id){
        return cinemaService.getById(id);
   }
   @GetMapping("/cinema/all")
    public HashSet<Cinema> getAll(){
        HashSet<Cinema>cinemas=cinemaService.getAll();
        return cinemas;
   }
   @DeleteMapping("/cinemas/{id}")

    public Cinema remove(@PathVariable int id, HttpSession session){
       int userId=loggedId(session);
       Cinema cinema=cinemaService.remove(id,userId);
        return cinema;
   }


}



