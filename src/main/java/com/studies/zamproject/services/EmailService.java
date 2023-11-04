/* (C)2023 */
package com.studies.zamproject.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String sender;

    public void sendVerificationEmail(String to) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setFrom(sender);
        message.setSubject("Witaj nowy organizatorze!");
        message.setText(
                "Aby dokończyć proces rejestracji potrzebujesz przesłać nam dokumenty"
                        + " potwierdzające twój status podmiotu gospodarczego.");
        mailSender.send(message);
    }
}