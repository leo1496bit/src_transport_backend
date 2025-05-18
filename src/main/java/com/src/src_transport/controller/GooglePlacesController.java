package com.src.src_transport.controller;

import com.src.src_transport.model.Point;
import com.src.src_transport.service.GooglePlacesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin(origins = {"${api.url}"})
public class GooglePlacesController {
    @Autowired
    private GooglePlacesService googlePlacesService;

    @GetMapping("/api/autocomplete")
    public Map<String, Object> getAutocompleteSuggestions(
            @RequestParam String input,
            @RequestParam(required = false) String sessionToken) {

        // Utiliser une session token ou générer une par défaut
        if (sessionToken == null || sessionToken.isEmpty()) {
            sessionToken = String.valueOf(System.currentTimeMillis());
        }

        return googlePlacesService.getAutocompleteSuggestions(input, sessionToken);
    }

    @GetMapping("/api/get-coordinates")
    public Point getCoordinates(@RequestParam String placeId) {
        return googlePlacesService.getCoordinatesFromPlaceId(placeId);
    }
}
