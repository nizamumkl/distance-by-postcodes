package com.nizam.geo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
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
import org.springframework.beans.factory.annotation.Autowired;

import com.nizam.geo.entity.PostcodeEntity;
import com.nizam.geo.repository.PostcodeRepository;

public class PostcodeServiceTest {

    @Mock
    private PostcodeRepository postcodeRepository;

    @InjectMocks
    private PostcodeService postcodeService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindByPostcode() {
        String postcode = "ABC 123";
        PostcodeEntity mockEntity = new PostcodeEntity();
        mockEntity.setPostcode(postcode);
        mockEntity.setLatitude(50.2125650);
        mockEntity.setLongitude(-5.2977680);
        
        
        when(postcodeRepository.findByPostcode(postcode)).thenReturn(mockEntity);

        PostcodeEntity result = postcodeService.findByPostcode(postcode);

        assertEquals(mockEntity, result);
    }

    @Test
    public void testGetAllPostcodes() {
    	PostcodeEntity mockEntity1 = new PostcodeEntity();
    	mockEntity1.setPostcode("ABC 123");
    	mockEntity1.setLatitude(50.2125650);
    	mockEntity1.setLongitude(-5.2977680);
    	
    	PostcodeEntity mockEntity2 = new PostcodeEntity();
    	mockEntity1.setPostcode("XYZ 789");
    	mockEntity1.setLatitude(51.2125650);
    	mockEntity1.setLongitude(-6.2977680);
    	
        List<PostcodeEntity> mockEntities = Arrays.asList(mockEntity1, mockEntity2);
        when(postcodeRepository.findAll()).thenReturn(mockEntities);

        List<PostcodeEntity> result = postcodeService.getAllPostcodes();

        assertEquals(2, result.size());
        assertEquals(mockEntities.get(0), result.get(0));
    }

    @Test
    public void testUpdatePostcodeCoordinates_EntityExists() {
        String postcode = "ABC 123";
        double latitude = 50.2125650;
        double longitude = -5.2977680;
        PostcodeEntity mockEntity = new PostcodeEntity(); // Existing entity
        mockEntity.setPostcode(postcode);
        mockEntity.setLatitude(latitude);
        mockEntity.setLongitude(longitude);
        
        when(postcodeRepository.findByPostcode(postcode)).thenReturn(mockEntity);

        double newLatitude = 50.50;
        double newLongitude = -5.50;
        postcodeService.updatePostcodeCoordinates(postcode, newLatitude, newLongitude);

        verify(postcodeRepository, times(1)).save(mockEntity);
        assertEquals(newLatitude, mockEntity.getLatitude());
        assertEquals(newLongitude, mockEntity.getLongitude());
    }

    @Test
    public void testUpdatePostcodeCoordinates_EntityNotFound() {
        String postcode = "99999"; // Assuming this postcode does not exist
        double latitude = 41.0;
        double longitude = -72.0;
        when(postcodeRepository.findByPostcode(postcode)).thenReturn(null);

        postcodeService.updatePostcodeCoordinates(postcode, latitude, longitude);

        verify(postcodeRepository, never()).save(any());
    }
    
}
