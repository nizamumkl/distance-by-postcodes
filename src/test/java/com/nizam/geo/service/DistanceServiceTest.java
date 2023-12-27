package com.nizam.geo.service;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DistanceServiceTest {

    private final double DELTA = 0.001; // Allowed difference in double comparison

    @Test
    public void testCalculateDistance() {
        DistanceService distanceService = new DistanceService();

        // Coordinates for two locations
        double lat1 = 40.7128; 		// City01 latitude
        double lon1 = -74.0060; 	// City01 longitude
        double lat2 = 34.0522; 		// City02 latitude
        double lon2 = -118.2437;	// City02 longitude

        double expectedDistance = 3933.5; // Approximate distance in kilometers between City01 and City02

        // Calculate distance using the service method
        double calculatedDistance = distanceService.calculateDistance(lat1, lon1, lat2, lon2);

        // Assert that the calculated distance is within an acceptable delta of the expected value
        assertEquals(expectedDistance, calculatedDistance, DELTA);
    }
    
}

