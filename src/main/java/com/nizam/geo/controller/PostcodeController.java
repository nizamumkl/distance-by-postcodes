package com.nizam.geo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.nizam.geo.dto.PostcodeDTO;
import com.nizam.geo.entity.PostcodeEntity;
import com.nizam.geo.exception.InvalidRequestException;
import com.nizam.geo.service.PostcodeService;

import jakarta.validation.Valid;

@ControllerAdvice
@RestController
public class PostcodeController {

    @Autowired
    private PostcodeService postcodeService;

    @GetMapping("/postcodes")
    public List<PostcodeEntity> getAllPostcodes() {
        return postcodeService.getAllPostcodes();
    }

    @GetMapping("/postcodes/{postcode}")
    public ResponseEntity<PostcodeEntity> getPostcode(@PathVariable String postcode) {
        PostcodeEntity entity = postcodeService.findByPostcode(postcode);
        if (entity != null) {
            return ResponseEntity.ok().body(entity);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/postcodes/{postcode}")
    public ResponseEntity<String> updatePostcode(@PathVariable String postcode, @Valid @RequestBody PostcodeDTO postcodeDTO,
    		BindingResult bindingResult) {
    	if (bindingResult.hasErrors()) {
            throw new InvalidRequestException("Invalid request: " + bindingResult.getAllErrors());
        }
    	
        postcodeService.updatePostcodeCoordinates(postcode, postcodeDTO.getLatitude(), postcodeDTO.getLongitude());
        return ResponseEntity.ok("Post code coordinates updated successfully");
    }
    
    // Exception handler for handling InvalidRequestException
    @ExceptionHandler(InvalidRequestException.class)
    public ResponseEntity<String> handleInvalidRequestException(InvalidRequestException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
    
}
