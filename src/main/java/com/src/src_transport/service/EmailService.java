package com.src.src_transport.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendSimpleEmail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        message.setFrom("noreply@carliine.com");

        mailSender.send(message);
    }
    public void sendEmailWithAttachment(String to, String subject, String text, String attachmentPath) throws MessagingException {
        // Créer un MimeMessage
        MimeMessage message = mailSender.createMimeMessage();

        // Utiliser MimeMessageHelper pour ajouter des pièces jointes
        MimeMessageHelper helper = new MimeMessageHelper(message, true); // true pour pièce jointe
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(text,true);
        helper.setFrom("noreply@carliine.com");

        // Ajouter la pièce jointe
        FileSystemResource file = new FileSystemResource(new File(attachmentPath));
        if (file.exists()) {
            helper.addAttachment(file.getFilename(), file);
        } else {
            throw new MessagingException("Le fichier " + attachmentPath + " est introuvable !");
        }

        // Envoyer l'email
        mailSender.send(message);
    }
}

