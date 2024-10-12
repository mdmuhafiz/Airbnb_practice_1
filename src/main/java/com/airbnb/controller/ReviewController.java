package com.airbnb.controller;

import com.airbnb.entity.Property;
import com.airbnb.entity.PropertyUser;
import com.airbnb.entity.Review;
import com.airbnb.repository.PropertyRepository;
import com.airbnb.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/reviews")
public class ReviewController {
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private PropertyRepository propertyRepository;



    @PostMapping("/addReview/{propertyId}")
    public ResponseEntity<String> addReview(@PathVariable long propertyId,
                                            @AuthenticationPrincipal PropertyUser user,
                                            @RequestBody Review review){

        Optional<Property> opProperty = propertyRepository.findById(propertyId);
        Property property = opProperty.get();
        Review r = reviewRepository.findReviewByUser(property, user);
        if(r!=null){
            return new ResponseEntity<>("You have already added a review",HttpStatus.BAD_REQUEST);
        }
        review.setProperty(property);
        review.setPropertyUser(user);
        reviewRepository.save(review);

        return new ResponseEntity<>("Review is added succesfully", HttpStatus.CREATED);
    }

    @GetMapping("/userReviews")
    public ResponseEntity<List<Review>> getuserReviews(@AuthenticationPrincipal PropertyUser user){
        List<Review> reviews = reviewRepository.findByPropertyUser(user);
        return new ResponseEntity<>(reviews,HttpStatus.OK);
    }


}
