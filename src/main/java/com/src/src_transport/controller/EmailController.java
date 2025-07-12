package com.src.src_transport.controller;

import com.src.src_transport.model.EmailRequest;
import com.src.src_transport.service.EmailService;
import com.src.src_transport.service.MailGunService;
import feign.FeignException;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/emails")
public class EmailController {

    @Autowired
    private EmailService emailService;
    @Autowired
    private MailGunService mailGunService;

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

    @CrossOrigin(origins = "${api.url}")
    @PostMapping("/send-with-attachments")
    public String sendEmailWithAttachements(
            @RequestParam String to,
            @RequestParam String subject,
            @RequestParam String body,
            @RequestParam List<MultipartFile> attachments) throws MessagingException, IOException {

        // Convertir les fichiers reçus en liste de fichiers
        File[] files = new File[attachments.size()];
        for (int i = 0; i < attachments.size(); i++) {
            File file = File.createTempFile("attachment-", attachments.get(i).getOriginalFilename());
            attachments.get(i).transferTo(file);
            files[i] = file;
        }

        // Appeler le service pour envoyer l'email
        emailService.sendEmailWithAttachments(to, subject, body, Arrays.stream(files).map(File::getAbsolutePath).collect(Collectors.toList()));

        // Après l'envoi de l'e-mail, supprimer les fichiers temporaires
        for (File file : files) {
            file.delete();
        }

        return "E-mail envoyé avec succès!";
    }

    @CrossOrigin(origins = "${api.url}")
    @PostMapping("/mailgun/send")
    public ResponseEntity<String> mailGunSendEmail(@RequestBody EmailRequest request) {
        try {
            mailGunService.sendSimpleMail(request.getTo(), request.getSubject(), request.getText());
            return ResponseEntity.ok("Email envoyé avec succès !");
        } catch (FeignException e) {
            return ResponseEntity.status(e.status()).body(e.getMessage());
        }
    }

    @CrossOrigin(origins = "${api.url}")
    @PostMapping("/mailgun/send-with-attachments")
    public ResponseEntity<String> mailGunSendEmailWithAttachements(
            @RequestParam String to,
            @RequestParam String subject,
            @RequestParam String body,
            @RequestParam List<MultipartFile> attachments) throws  IOException {

        // Convertir les fichiers reçus en liste de fichiers
        File[] files = new File[attachments.size()];
        for (int i = 0; i < attachments.size(); i++) {
            File file = File.createTempFile("attachment-", attachments.get(i).getOriginalFilename());
            attachments.get(i).transferTo(file);
            files[i] = file;
        }
        try {
            mailGunService.sendEmailWithAttachments(to, subject, body, Arrays.stream(files).map(File::getAbsolutePath).collect(Collectors.toList()));
            return ResponseEntity.ok("Email envoyé avec succès !");
        } catch (FeignException e) {
            return ResponseEntity.status(e.status()).body(e.getMessage());
        }
        finally {
            for (File file : files) {
                file.delete();
            }
        }

    }
    @CrossOrigin(origins = "${api.url}")
    @PostMapping("/mailgun/send-with-attachment")
    public ResponseEntity<String> maiGunsendEmailWithAttachment(
            @RequestParam String to,
            @RequestParam String subject,
            @RequestParam String text,
            @RequestParam MultipartFile attachment) {

        try {
            // Sauvegarder le fichier temporairement sur le serveur
            File tempFile = File.createTempFile("attachment-", attachment.getOriginalFilename());
            attachment.transferTo(tempFile);

            // Appeler le service pour envoyer l'email
            mailGunService.sendEmailWithAttachment(to, subject, text, tempFile.getAbsolutePath());
            tempFile.delete();
            return ResponseEntity.ok("Email envoyé avec succès !");
        } catch (FeignException | IOException e) {
            return ResponseEntity.status(500).body("Erreur lors de l'envoi de l'email : " + e.getMessage());
        }
    }
}

