/* (C)2023 */
package com.studies.zamproject.services;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    public void sendVerificationEmail(String to) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Witaj nowy organizatorze!");
        message.setText(
                "Aby dokończyć proces rejestracji potrzebujesz przesłać nam dokumenty"
                        + " potwierdzające twój status podmiotu gospodarczego.");
        mailSender.send(message);
    }
}
