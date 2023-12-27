package com.nizam.geo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.nizam.geo.entity.PostcodeEntity;
import com.nizam.geo.model.Location;
import com.nizam.geo.response.DistanceResponse;
import com.nizam.geo.service.DistanceService;
import com.nizam.geo.service.PostcodeService;

@RestController
public class DistanceController {

    @Autowired
    private PostcodeService postcodeService;

    @Autowired
    private DistanceService distanceService;

//    @Secured("ROLE_USER")
    @GetMapping("/distance/{postcode1}/{postcode2}")
    public ResponseEntity<DistanceResponse> getDistance(@PathVariable String postcode1, @PathVariable String postcode2) {
        PostcodeEntity location1 = postcodeService.findByPostcode(postcode1);
        PostcodeEntity location2 = postcodeService.findByPostcode(postcode2);

        if (location1 == null || location2 == null) {
            return ResponseEntity.notFound().build();
        }

        double distance = distanceService.calculateDistance(
                location1.getLatitude(), location1.getLongitude(),
                location2.getLatitude(), location2.getLongitude()
        );

        DistanceResponse response = new DistanceResponse(
                new Location(postcode1, location1.getLatitude(), location1.getLongitude()),
                new Location(postcode2, location2.getLatitude(), location2.getLongitude()),
                distance, "km"
        );

        return ResponseEntity.ok().body(response);
    }
    
}
