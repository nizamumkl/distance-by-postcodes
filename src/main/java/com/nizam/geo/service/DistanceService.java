package com.nizam.geo.service;

import org.apache.commons.math3.util.Precision;
import org.springframework.stereotype.Service;

@Service
public class DistanceService {

	private final static double EARTH_RADIUS = 6371; // radius in kilometers
	
	private double haversine(double deg1, double deg2) {
		return square(Math.sin((deg1 - deg2) / 2.0));
	}
	
	private double square(double x) {
		return x * x;
	}

    public double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        // Using Haversine formula! See Wikipedia;
		double lon1Radians = Math.toRadians(lon1);
		double lon2Radians = Math.toRadians(lon2);
		double lat1Radians = Math.toRadians(lat1);
		double lat2Radians = Math.toRadians(lat2);
		double a = haversine(lat1Radians, lat2Radians)
					+ Math.cos(lat1Radians) * Math.cos(lat2Radians) * haversine(lon1Radians, lon2Radians);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

		return Precision.round(EARTH_RADIUS * c, 1);
    }
}
