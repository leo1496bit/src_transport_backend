package com.src.src_transport.service;

import com.google.maps.DistanceMatrixApi;
import com.google.maps.GeoApiContext;
import com.google.maps.model.LatLng;
import com.google.maps.model.TravelMode;
import com.src.src_transport.model.Distance;
import lombok.AllArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DistanceMatrixService {
    private final Environment environment;
    public long getDistance(Distance distance) {
        GeoApiContext context = new GeoApiContext.Builder().apiKey(environment.getProperty("google.api.key")).build();
        try {
            return DistanceMatrixApi.newRequest(context).origins(new LatLng(distance.getOrigins()[0],distance.getOrigins()[1])).destinations(new LatLng(distance.getDestinations()[0],distance.getDestinations()[1])).mode(TravelMode.DRIVING).await().rows[0].elements[0].distance.inMeters;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
