package com.src.src_transport.controller;

import com.src.src_transport.model.EmailBrevoRequest;
import com.src.src_transport.service.BrevoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/brevo/emails")
@CrossOrigin(origins = "${api.url}")
public class EmailBrevoController {
    @Autowired
    private BrevoService brevoService;

    @PostMapping("/send")
    public ResponseEntity<String> sendEmail(@RequestBody EmailBrevoRequest emailRequest) {
        String response = brevoService.sendEmail(emailRequest);
        return ResponseEntity.ok(response);
    }
}
