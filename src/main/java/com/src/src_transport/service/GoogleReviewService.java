package com.src.src_transport.service;

import com.src.src_transport.model.GoogleReview;
import lombok.AllArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class GoogleReviewService {
    private final Environment environment;

    private static final String PLACE_DETAILS_URL = "https://maps.googleapis.com/maps/api/place/details/json";
    public List<GoogleReview> getReviews() {
        String placeId = "ChIJZepUMpBv5kcRIpEFqT0cv9c";
        RestTemplate restTemplate = new RestTemplate();

        String url = UriComponentsBuilder.fromHttpUrl(PLACE_DETAILS_URL)
                .queryParam("place_id", placeId)
                .queryParam("fields", "reviews")
                .queryParam("key", environment.getProperty("google.api.key"))
                .queryParam("language","fr")
                .toUriString();

        Map<String, Object> response = restTemplate.getForObject(url, Map.class);
        Map<String, Object> result = (Map<String, Object>) response.get("result");
        if (result == null || !result.containsKey("reviews")) {
            return List.of();
        }

        List<Map<String, Object>> reviews = (List<Map<String, Object>>) result.get("reviews");

        return reviews.stream()
                .map(review -> new GoogleReview(
                        (String) review.get("author_name"),
                        (String) review.get("profile_photo_url"),
                        (Integer) review.get("rating"),
                        (String) review.get("relative_time_description"),
                        (String) review.get("text")
                ))
                .collect(Collectors.toList());
    }
}
