package com.synex.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.synex.domain.Booking;

public interface BookingRepository extends JpaRepository<Booking, Integer>{
	
	Booking findByCustomerMobile(long customerMobile);
	
	List<Booking> findByUserName(String userName);

}
