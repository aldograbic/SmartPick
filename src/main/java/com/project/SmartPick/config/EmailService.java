package com.project.SmartPick.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class EmailService {

    @Autowired
    private JavaMailSender emailSender;
    
    @Value("${spring.mail.username}")
    private String sender;

    public void sendMessage(String to, String subject, String text) {

        SimpleMailMessage message = new SimpleMailMessage(); 
        message.setFrom(sender);
        message.setTo(to); 
        message.setSubject(subject); 
        message.setText(text);

        emailSender.send(message);
    }
}