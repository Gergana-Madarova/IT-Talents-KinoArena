package com.example.kinoarenaproject.model.DTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EditProfileDTO {
    private int id;
    private  String first_name;
    private String last_name;
    private String email;
    private String password;
    private String newPassword;
    private String confirmNewPassword;
    private Date birth_date;
//    private int city_id;
    private String gender;
    private String phone_number;
    private String role_name;
}
