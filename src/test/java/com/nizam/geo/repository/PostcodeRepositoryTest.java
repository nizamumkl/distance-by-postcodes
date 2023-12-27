package com.nizam.geo.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import com.nizam.geo.entity.PostcodeEntity;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class PostcodeRepositoryTest {

    @Autowired
    private PostcodeRepository postalCodeRepository;

    @Test
    void findByPostcode_ReturnsPostcodeEntity() {
        // Given
        PostcodeEntity expectedEntity = new PostcodeEntity();
        expectedEntity.setPostcode("SW1A");
        expectedEntity.setLatitude(51.5014);
        expectedEntity.setLongitude(-0.1419);
        postalCodeRepository.save(expectedEntity);

        // When
        PostcodeEntity actualEntity = postalCodeRepository.findByPostcode("SW1A");

        // Then
        assertNotNull(actualEntity);
        assertEquals(expectedEntity.getPostCode(), actualEntity.getPostCode());
        assertEquals(expectedEntity.getLatitude(), actualEntity.getLatitude());
        assertEquals(expectedEntity.getLongitude(), actualEntity.getLongitude());
    }
    
}
