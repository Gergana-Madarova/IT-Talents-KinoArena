package com.example.kinoarenaproject.controller;


    import com.example.kinoarenaproject.model.DTOs.LoginDTO;
    import com.example.kinoarenaproject.model.DTOs.RegisterDTO;
    import com.example.kinoarenaproject.model.exceptions.UnauthorizedException;
    import jakarta.servlet.http.HttpSession;

    import java.util.regex.Matcher;
import java.util.regex.Pattern;

    public class ValidationUtils {

        private static final String EMAIL_REGEX = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
        private static final String PHONE_REGEX = "^\\+(?:[0-9] ?){6,14}[0-9]$";
        private static final String PASSWORD_REGEX = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";

        public static boolean isValidEmail(String email) {
            Pattern pattern = Pattern.compile(EMAIL_REGEX);
            Matcher matcher = pattern.matcher(email);
            return matcher.matches();
        }

        public static boolean isValidPhoneNumber(String phoneNumber) {
            Pattern pattern = Pattern.compile(PHONE_REGEX);
            Matcher matcher = pattern.matcher(phoneNumber);
            return matcher.matches();
        }

        public static boolean isValidPassword(String password) {
            Pattern pattern = Pattern.compile(PASSWORD_REGEX);
            Matcher matcher = pattern.matcher(password);
            return matcher.matches();
        }
        public static boolean validEmail(RegisterDTO registerData){
        return  isValidEmail(registerData.getEmail());
        }
        public static boolean validPassword(RegisterDTO registerData){
            return isValidPassword(registerData.getPassword());
        }
        public static boolean validPhoneNumber(RegisterDTO registerData){
            return isValidPhoneNumber(registerData.getPhone_number());
        }



}
