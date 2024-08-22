package com.synex.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.synex.domain.Review;
import com.synex.repository.ReviewRepository;

@Service
public class ReviewService {
	
	@Autowired ReviewRepository reviewRepository;
	
	public Review saveReview(Review review) {
		return reviewRepository.save(review);
	}
	
	public List<Review> getReviewsByHotelId(int hotelId) {
        return reviewRepository.findReviewsByHotelId(hotelId);
    }
	
	

}
