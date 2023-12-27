package com.nizam.geo.dto;

import jakarta.validation.constraints.NotNull;

public class PostcodeDTO {
	
	@NotNull(message = "Postcode cannot be null")
    private String postcode;
	
	@NotNull(message = "Latitude cannot be null")
    private double latitude;
	
	@NotNull(message = "longitude cannot be null")
    private double longitude;
    
    public String getPostcode() {
		return postcode;
	}
    
	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}
	
	public double getLatitude() {
		return latitude;
	}
	
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	
	public double getLongitude() {
		return longitude;
	}
	
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

}
