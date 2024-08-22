package com.rev_connect_api.services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    //Injecting the JavaMailSender bean to send emails
    @Autowired
    private JavaMailSender emailSender;

    //The email address from which the emails will be sent, loaded from application properties
    @Value("${spring.mail.username}")
    private String fromAddress;


     //Sends an email with the specified recipient, subject, and body.

    public void sendEmail(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo();
        message.setSubject(subject);
        message.setText(body);
        message.setFrom("learner@gmail.com");
        try {
            emailSender.send(message);
            System.out.println("Email sent successfully to: " + to);
        } catch (Exception e) {
            System.err.println("Error sending email to " + to + ": " + e.getMessage());
            e.printStackTrace();
        }
    }
}