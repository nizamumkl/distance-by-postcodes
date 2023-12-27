package com.nizam.geo.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import com.nizam.geo.dto.PostcodeDTO;
import com.nizam.geo.entity.PostcodeEntity;
import com.nizam.geo.service.PostcodeService;

public class PostcodeControllerTest {
    
	@Mock
    private PostcodeService postcodeService;

    @InjectMocks
    private PostcodeController postcodeController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllPostcodes() {
        // Mocking the behavior of postcodeService.getAllPostcodes()
    	
    	PostcodeEntity mockEntity1 = new PostcodeEntity();
    	mockEntity1.setPostcode("ABC 123");
    	mockEntity1.setLatitude(50.2125650);
    	mockEntity1.setLongitude(-5.2977680);
    	
    	PostcodeEntity mockEntity2 = new PostcodeEntity();
    	mockEntity1.setPostcode("XYZ 789");
    	mockEntity1.setLatitude(51.2125650);
    	mockEntity1.setLongitude(-6.2977680);
    	
        List<PostcodeEntity> mockPostcodes = Arrays.asList(mockEntity1, mockEntity2);
        when(postcodeService.getAllPostcodes()).thenReturn(mockPostcodes);

        List<PostcodeEntity> result = postcodeController.getAllPostcodes(); 
        
        assertEquals(2, result.size());
    }

    @Test
    public void testGetPostcode_Success() {
        String postcode = "ABC 123";

        PostcodeEntity mockEntity = new PostcodeEntity();
        mockEntity.setPostcode(postcode);
        mockEntity.setLatitude(50.2125650);
        mockEntity.setLongitude(-5.2977680);
        
        when(postcodeService.findByPostcode(postcode)).thenReturn(mockEntity);

        ResponseEntity<PostcodeEntity> responseEntity = postcodeController.getPostcode(postcode);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(mockEntity, responseEntity.getBody());
    }

    @Test
    public void testGetPostcode_NotFound() {
        String postcode = "99999"; // Assuming this postcode does not exist
        when(postcodeService.findByPostcode(postcode)).thenReturn(null);

        ResponseEntity<PostcodeEntity> responseEntity = postcodeController.getPostcode(postcode);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }
    
    @Test
    public void testUpdatePostcode_Success() {
        // Mock data
        String postcode = "ABC123";
        PostcodeDTO mockPostcodeDTO = new PostcodeDTO();
        mockPostcodeDTO.setLatitude(51.5074);
        mockPostcodeDTO.setLongitude(-0.1278);

        // Mock successful update in service layer
        when(postcodeService.updatePostcodeCoordinates(anyString(), anyDouble(), anyDouble())).thenReturn(true);

        // Call the controller method
        ResponseEntity<String> responseEntity = postcodeController.updatePostcode(postcode, mockPostcodeDTO, mock(BindingResult.class));

        // Verify the behavior
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Post code coordinates updated successfully", responseEntity.getBody());

        // Verify that the service method was called with the correct arguments
        verify(postcodeService, times(1)).updatePostcodeCoordinates(eq(postcode), eq(mockPostcodeDTO.getLatitude()), eq(mockPostcodeDTO.getLongitude()));
    }

    @Test
    public void testUpdatePostcode_InvalidRequest() {
        // Mock data for invalid request scenario
        String postcode = "InvalidPostcode";
        PostcodeDTO mockPostcodeDTO = new PostcodeDTO();
        mockPostcodeDTO.setPostcode(null); // invalid RequestBody
        mockPostcodeDTO.setLatitude(1000.0);
        mockPostcodeDTO.setLongitude(-2000.0);

        // Call the controller method with invalid request data
        ResponseEntity<String> responseEntity = postcodeController.updatePostcode(postcode, mockPostcodeDTO, mock(BindingResult.class));

        // Verify the behavior
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

}
