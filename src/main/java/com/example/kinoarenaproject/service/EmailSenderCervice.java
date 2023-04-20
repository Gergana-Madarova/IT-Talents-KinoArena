
package com.example.kinoarenaproject.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@Service
public class EmailSenderCervice {

    @Autowired
    private org.springframework.mail.javamail.JavaMailSender mailSender;
//    private JavaMailSender mailSender;

    public void sendEmail(String toEmail, String subject, String body){
        SimpleMailMessage message =new SimpleMailMessage();
        message.setFrom("margot11margot11@gmail.com");
        message.setTo(toEmail);
        message.setText(body);
        message.setSubject(subject);

        mailSender.send(message);

        System.out.println("Mail sent successfully");

    }
}
