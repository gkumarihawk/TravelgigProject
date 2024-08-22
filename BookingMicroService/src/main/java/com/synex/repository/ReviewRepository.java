package com.synex.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.synex.domain.Guest;
import com.synex.domain.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer>{
	
	@Query("SELECT r FROM Review r JOIN r.booking b WHERE b.hotelId = :hotelId")
    List<Review> findReviewsByHotelId(@Param("hotelId") int hotelId);

}