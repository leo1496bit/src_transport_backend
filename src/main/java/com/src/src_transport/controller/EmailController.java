package com.src.src_transport.controller;

import com.src.src_transport.model.EmailRequest;
import com.src.src_transport.service.EmailService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/api/emails")
public class EmailController {

    @Autowired
    private EmailService emailService;

    @CrossOrigin(origins = "${api.url}")
    @PostMapping("/send")
    public ResponseEntity<String> sendEmail(@RequestBody EmailRequest request) {
        try {
            emailService.sendSimpleEmail(request.getTo(), request.getSubject(), request.getText());
            return ResponseEntity.ok("Email envoyé avec succès !");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Erreur lors de l'envoi de l'email.");
        }
    }
    @CrossOrigin(origins = "${api.url}")
    @PostMapping("/send-with-attachment")
    public ResponseEntity<String> sendEmailWithAttachment(
            @RequestParam String to,
            @RequestParam String subject,
            @RequestParam String text,
            @RequestParam MultipartFile attachment) {

        try {
            // Sauvegarder le fichier temporairement sur le serveur
            File tempFile = File.createTempFile("attachment-", attachment.getOriginalFilename());
            attachment.transferTo(tempFile);

            // Appeler le service pour envoyer l'email
            emailService.sendEmailWithAttachment(to, subject, text, tempFile.getAbsolutePath());

            // Supprimer le fichier temporaire
            tempFile.delete();

            return ResponseEntity.ok("Email avec pièce jointe envoyé avec succès !");
        } catch (MessagingException | IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Erreur lors de l'envoi de l'email : " + e.getMessage());
        }
    }
}

