package com.nizam.geo.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.nizam.geo.entity.PostcodeEntity;
import com.nizam.geo.response.DistanceResponse;
import com.nizam.geo.service.DistanceService;
import com.nizam.geo.service.PostcodeService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class DistanceControllerTest {

    @Mock
    private PostcodeService postcodeService;

    @Mock
    private DistanceService distanceService;

    @InjectMocks
    private DistanceController distanceController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetDistance_Success() {
        String postcode1 = "ABC 123";
        String postcode2 = "XYZ 789";
        double distanceResult = 50.0; // Assuming a distance of 50 km

        // Mocking the behavior of postcodeService.findByPostcode()
        PostcodeEntity location1 = new PostcodeEntity();
        location1.setPostcode(postcode1);
        location1.setLongitude(50.0);
        location1.setLongitude(-5.0);
        
        PostcodeEntity location2 = new PostcodeEntity();
        location1.setPostcode(postcode2);
        location1.setLongitude(50.1);
        location1.setLongitude(-5.1);
        
        when(postcodeService.findByPostcode(postcode1)).thenReturn(location1);
        when(postcodeService.findByPostcode(postcode2)).thenReturn(location2);

        // Mocking the behavior of distanceService.calculateDistance()
        when(distanceService.calculateDistance(location1.getLatitude(), location1.getLongitude(),
                location2.getLatitude(), location2.getLongitude())).thenReturn(distanceResult);

        // Call the controller method
        ResponseEntity<DistanceResponse> responseEntity = distanceController.getDistance(postcode1, postcode2);

        // Verify the response
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void testGetDistance_LocationNotFound() {
        String postcode1 = "ABC 123";
        String postcode2 = "XYZ 789";

        // Mocking behavior when locations are not found
        when(postcodeService.findByPostcode(postcode1)).thenReturn(null);
        when(postcodeService.findByPostcode(postcode2)).thenReturn(null);

        // Call the controller method
        ResponseEntity<DistanceResponse> responseEntity = distanceController.getDistance(postcode1, postcode2);

        // Verify the response
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }
    
}
