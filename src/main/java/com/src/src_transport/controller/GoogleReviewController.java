package com.src.src_transport.controller;

import com.src.src_transport.model.GoogleReview;
import com.src.src_transport.service.GoogleReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = {"${api.url}"})
@RequestMapping("/api/reviews")
public class GoogleReviewController {

    @Autowired
    private GoogleReviewService googleReviewService;

    @GetMapping("/")
    public List<GoogleReview> getReviews() {
        return googleReviewService.getReviews();
    }
}