package com.example.kinoarenaproject;
import com.example.kinoarenaproject.service.EmailSenderCervice;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableScheduling
@SpringBootApplication
public class KinoArenaProjectApplication {
    @Autowired
    private EmailSenderCervice senderCervice;

    public static void main(String[] args) {
        SpringApplication.run(KinoArenaProjectApplication.class, args);

    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


//    @EventListener(ApplicationReadyEvent.class)
//    public void sendEmail(){
//        senderCervice.sendEmail(
//                "margot11margot11@gmail.com",
//                "This is subject",
//                "Hi! I'm Tanya.");
//    }


}
