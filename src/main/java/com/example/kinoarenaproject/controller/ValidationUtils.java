package com.example.kinoarenaproject.controller;


    import com.example.kinoarenaproject.model.DTOs.EditProfileDTO;
    import com.example.kinoarenaproject.model.DTOs.LoginDTO;
    import com.example.kinoarenaproject.model.DTOs.RegisterDTO;
    import com.example.kinoarenaproject.model.exceptions.BadRequestException;
    import com.example.kinoarenaproject.model.exceptions.UnauthorizedException;
    import jakarta.servlet.http.HttpSession;

    import java.time.LocalDate;
    import java.time.LocalDateTime;
    import java.util.regex.Matcher;
import java.util.regex.Pattern;

    public class ValidationUtils {

        private static final String EMAIL_REGEX = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
        private static final String PHONE_REGEX = "^(\\+\\d{1,2})?\\s*(\\d{10})$";
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
        public static boolean validRegisterData(RegisterDTO registerDTO){
            if(!isValidEmail(registerDTO.getEmail())){
                throw new UnauthorizedException("No spaces,onlyASCII characters,no more than 254 symbols,valid top-level domain");
            }
            if(!isValidPhoneNumber(registerDTO.getPhone_number())){
                throw new UnauthorizedException("Ten digits,start with zero");
            }
            if(!isValidPassword(registerDTO.getPassword())){
                throw new UnauthorizedException("Inadequate input for password.At least one upper case, one lower case,one digit,one special character minimum eight characters , max 20");
            }
            return true;
        }
        public static boolean validEditData(EditProfileDTO editProfileDTO){
            if(!isValidEmail(editProfileDTO.getEmail())){
                throw new UnauthorizedException("No spaces,onlyASCII characters,no more than 254 symbols,valid top-level domain");
            }
            if(!isValidPhoneNumber(editProfileDTO.getPhone_number())){
                throw new UnauthorizedException("Ten digits,start with zero");
            }
            validateBirthDate(editProfileDTO.getBirth_date());
        return true;
        }
        public static boolean validateBirthDate(LocalDate birthDate) {
            if (!birthDate.isBefore(LocalDate.now().minusYears(10)) && birthDate.isAfter(LocalDate.now().minusYears(120))) {
                throw new BadRequestException("Wrong birth date");
                }
       return true;
        }
        public static boolean validEditProfilData(EditProfileDTO editProfileDTO){
            if( (editProfileDTO.getFirst_name()==null || editProfileDTO.getLast_name()==null ||
                    editProfileDTO.getEmail()==null || editProfileDTO.getBirth_date()==null ||
                    editProfileDTO.getCity_id()==0 || editProfileDTO.getGender()==null ||
                    editProfileDTO.getPhone_number()==null )) {
                throw new BadRequestException("Fields cannot be null");
            }
            return true;
        }




}
