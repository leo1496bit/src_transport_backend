package com.src.src_transport.service;

import com.src.src_transport.model.GooglePlaceDetailsResponse;
import com.src.src_transport.model.Point;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class GooglePlacesService {

    private final Environment environment;

    private final RestTemplate restTemplate = new RestTemplate();
    public static String encode(String input) {
        return URLEncoder.encode(input, StandardCharsets.UTF_8);
    }


    public Map<String, Object> getAutocompleteSuggestions(String input, String sessionToken) {
        String url = UriComponentsBuilder.fromHttpUrl("https://maps.googleapis.com/maps/api/place/autocomplete/json")
                .queryParam("input", encode(input))
                .queryParam("key", environment.getProperty("google.api.key"))
                .queryParam("sessiontoken", sessionToken)
                .queryParam("language", "fr") // Optionnel : Langue des résultats
                .toUriString();
        log.info(url);

        return restTemplate.getForObject(url, Map.class);
    }
    public Point getCoordinatesFromPlaceId(String placeId) {
        // Construire l'URL pour l'API Place Details
        String url = UriComponentsBuilder.fromHttpUrl("https://maps.googleapis.com/maps/api/place/details/json")
                .queryParam("place_id", placeId)
                .queryParam("key", environment.getProperty("google.api.key"))
                .toUriString();

        // Appeler l'API via RestTemplate
        RestTemplate restTemplate = new RestTemplate();
        GooglePlaceDetailsResponse response = restTemplate.getForObject(url, GooglePlaceDetailsResponse.class);

        if (response != null && response.getStatus().equals("OK")) {
            // Récupérer les coordonnées
            double lat = response.getResult().getGeometry().getLocation().getLat();
            double lng = response.getResult().getGeometry().getLocation().getLng();
            return new Point(lat, lng);
        } else {
            throw new RuntimeException("Impossible de récupérer les coordonnées pour le place_id : " + placeId);
        }
    }
}
