package com.nizam.geo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nizam.geo.entity.PostcodeEntity;
import com.nizam.geo.repository.PostcodeRepository;

@Service
public class PostcodeService {

    @Autowired
    private PostcodeRepository postcodeRepository;

    public PostcodeEntity findByPostcode(String postcode) {
        return postcodeRepository.findByPostcode(postcode);
    }

    public List<PostcodeEntity> getAllPostcodes() {
        return postcodeRepository.findAll();
    }

    public boolean updatePostcodeCoordinates(String postcode, double latitude, double longitude) {
        PostcodeEntity entity = postcodeRepository.findByPostcode(postcode);
        if (entity != null) {
            entity.setLatitude(latitude);
            entity.setLongitude(longitude);
            postcodeRepository.save(entity);
        }
        
        return true;
    }
    
//    public boolean updatePostcodeCoordinates(String postcode, double latitude, double longitude) {
//        PostcodeEntity entity = postcodeRepository.findByPostcode(postcode);
//        if (entity != null) {
//            entity.setLatitude(latitude);
//            entity.setLongitude(longitude);
//            try {
//                postcodeRepository.save(entity);
//                return true; // Return true if update is successful
//            } catch (Exception e) {
//                // Handle exceptions such as DataAccessException or any specific exception thrown during save
//                e.printStackTrace(); // Consider logging the exception for troubleshooting
//                return false; // Return false if there was an issue during the update
//            }
//        }
//        return false; // Return false if entity is not found
//    }

}
