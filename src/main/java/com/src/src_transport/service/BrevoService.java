package com.src.src_transport.service;

import com.src.src_transport.model.EmailBrevoRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BrevoService {
    @Value("${brevo.api.url}")
    private String apiUrl;

    @Value("${brevo.api.key}")
    private String apiKey;

    @Value("${brevo.sender.email}")
    private String senderEmail;

    @Value("${brevo.sender.name}")
    private String senderName;

    private final RestTemplate restTemplate = new RestTemplate();

    public String sendEmail(EmailBrevoRequest request) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("api-key", apiKey);

        Map<String, Object> emailData = new HashMap<>();
        emailData.put("sender", Map.of("email", senderEmail, "name", senderName));
        emailData.put("replyTo", Map.of("email", senderEmail, "name", "Noreply"));
        emailData.put("to", request.getTo().stream().map(email-> Map.of("email",email)).toList());
        emailData.put("templateId", request.getTemplateId());
        emailData.put("params", request.getParams());

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(emailData, headers);
        if (request.getAttachments() != null && !request.getAttachments().isEmpty()) {
            List<Map<String, String>> attachments = new ArrayList<>();
            for (EmailBrevoRequest.Attachment attachment : request.getAttachments()) {
                Map<String, String> fileData = new HashMap<>();
                fileData.put("name", attachment.getName());
                fileData.put("content", attachment.getContent()); // Encodé en Base64
                fileData.put("type", attachment.getType());
                attachments.add(fileData);
            }
            emailData.put("attachment", attachments);
        }

        ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.POST, entity, String.class);

        return response.getBody();
    }
}
