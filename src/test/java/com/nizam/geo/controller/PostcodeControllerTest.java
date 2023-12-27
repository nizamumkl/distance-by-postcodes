package com.nizam.geo.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nizam.geo.dto.PostcodeDTO;
import com.nizam.geo.entity.PostcodeEntity;
import com.nizam.geo.service.PostcodeService;

public class PostcodeControllerTest {

	@Autowired
    private MockMvc mockMvc;
    
	@Mock
    private PostcodeService postcodeService;

    @InjectMocks
    private PostcodeController postcodeController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    	this.mockMvc = MockMvcBuilders.standaloneSetup(new PostcodeController()).build();
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
    public void testUpdatePostcode_Success() throws Exception {
        // Mock postcodeDTO for successful update
        PostcodeDTO postcodeDTO = new PostcodeDTO();
        postcodeDTO.setLatitude(51.5074);
        postcodeDTO.setLongitude(0.1278);

        // Mock service method behavior
        when(postcodeService.updatePostcodeCoordinates(anyString(), anyDouble(), anyDouble())).thenReturn(true);

        // Perform the mock MVC request
        mockMvc.perform(MockMvcRequestBuilders.put("/postcodes/12345")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(postcodeDTO)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Post code coordinates updated successfully"));
    }

    // Utility method to convert object to JSON string
    private String asJsonString(Object obj) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }

}
