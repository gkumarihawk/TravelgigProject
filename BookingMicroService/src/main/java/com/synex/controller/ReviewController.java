package com.synex.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.synex.domain.Review;
import com.synex.service.ReviewService;

@RestController
public class ReviewController {
	
	@Autowired ReviewService reviewService;
	
	@CrossOrigin
	@RequestMapping(value="/saveReview")
	public Review saveReview(@RequestBody Review review) {
		return reviewService.saveReview(review);
	}
	
	@CrossOrigin
	@GetMapping("/hotel/reviews/{hotelId}")
    public List<Review> getReviewsByHotelId(@PathVariable int hotelId) {
        return reviewService.getReviewsByHotelId(hotelId);
        
    }

}
