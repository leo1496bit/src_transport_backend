package com.src.src_transport.controller;

import com.src.src_transport.model.Distance;
import com.src.src_transport.service.DistanceMatrixService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DistanceMatrixController {
    @Autowired
    private DistanceMatrixService distanceMatrixService;

    @PostMapping("/getDistance")
    @CrossOrigin(origins = "${api.url}")
    public long getDistance(@RequestBody Distance distance){
        return  distanceMatrixService.getDistance(distance);
    }

}
