package com.nizam.geo.response;

import com.nizam.geo.model.Location;

public class DistanceResponse {
	
    private Location location1;
    private Location location2;
    private double distance;
    private String unit;
    
    // Constructor
    public DistanceResponse(Location location1, Location location2, double distance, String unit) {
    	this.location1 = location1;
    	this.location2 = location2;
    	this.distance = distance;
    	this.unit = unit;
    }
       
	public Location getLocation1() {
		return location1;
	}
	public void setLocation1(Location location1) {
		this.location1 = location1;
	}
	public Location getLocation2() {
		return location2;
	}
	public void setLocation2(Location location2) {
		this.location2 = location2;
	}
	public double getDistance() {
		return distance;
	}
	public void setDistance(double distance) {
		this.distance = distance;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}

    
}
