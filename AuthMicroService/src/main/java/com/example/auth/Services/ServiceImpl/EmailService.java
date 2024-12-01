package com.example.auth.Services.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender mailSender;

    @Async
    public void sendVerificationEmail(String email, String token) {
        try{
            String subject = "Email Verification";
            String verificationUrl = "http://your-domain.com/verify-email?token=" + token;
            String message = "Click the link below to verify your email address:\n" + verificationUrl;

            SimpleMailMessage emailMessage = new SimpleMailMessage();
            emailMessage.setTo(email);
            emailMessage.setSubject(subject);
            emailMessage.setText(message);
            mailSender.send(emailMessage);

        }
        catch(Exception e){
            System.err.println("Error sending email: " + e.getMessage());
        }
    }
}