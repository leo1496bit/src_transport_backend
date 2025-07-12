package com.src.src_transport.service;

import com.mailgun.api.v3.MailgunMessagesApi;
import com.mailgun.client.MailgunClient;
import com.mailgun.model.message.Message;
import com.mailgun.model.message.MessageResponse;
import feign.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

@Service
public class MailGunService {
    @Value("${mailgun.key}")
    private String apiKey;
    @Value("${domain}")
    private String domain;
    private final String EU_BASE_URL = "https://api.eu.mailgun.net/";

    public String sendEmailWithAttachments(String to, String subject, String text, List<String> attachmentPaths){
        MailgunMessagesApi mailgunMessagesApi = MailgunClient.config(EU_BASE_URL,apiKey)
                .logLevel(Logger.Level.BASIC)
                .createApi(MailgunMessagesApi.class);
        List<File> attachmentFiles = attachmentPaths.stream().map(attachmentPath->{
            FileSystemResource file = new FileSystemResource(new File(attachmentPath));
            if (file.exists() && file.isFile()) {
                return file.getFile();
            } else {
                throw new RuntimeException("Fichier introuvable ou invalide : " + attachmentPath);
            }
        }).toList();
        Message message = Message.builder()
                .from("noreply@carliine.com")
                .to(to)
                .subject(subject)
                .html(text)
                .attachment(attachmentFiles)
                .build();

        MessageResponse messageResponse = mailgunMessagesApi.sendMessage(domain,message);
        return messageResponse.getMessage();
    }

    public String sendEmailWithAttachment(String to, String subject, String text, String attachmentPath){
        MailgunMessagesApi mailgunMessagesApi = MailgunClient.config(EU_BASE_URL,apiKey)
                .logLevel(Logger.Level.BASIC)
                .createApi(MailgunMessagesApi.class);
        FileSystemResource file = new FileSystemResource(new File(attachmentPath));

        Message message = Message.builder()
                .from("noreply@carliine.com")
                .to(to)
                .subject(subject)
                .html(text+"<br/>")
                .attachment(file.getFile())
                .build();

        MessageResponse messageResponse = mailgunMessagesApi.sendMessage(domain,message);
        return messageResponse.getMessage();
    }
    public String sendSimpleMail(String to, String subject, String text){
        MailgunMessagesApi mailgunMessagesApi = MailgunClient.config(EU_BASE_URL,apiKey)
                .logLevel(Logger.Level.BASIC)
                .createApi(MailgunMessagesApi.class);
        Message message = Message.builder()
                .from("noreply@carliine.com")
                .to(to)
                .subject(subject)
                .html(text+"<br/>")
                .build();
        MessageResponse messageResponse = mailgunMessagesApi.sendMessage(domain,message);
        return messageResponse.getMessage();
    }


}