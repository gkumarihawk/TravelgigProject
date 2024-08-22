package com.synex.service;



import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.synex.domain.Booking;
import com.synex.repository.BookingRepository;


@Service
public class BookingService {
	
	@Autowired BookingRepository bookingRepository; 
	
	public Booking save(Booking booking) {
		return bookingRepository.save(booking);
	}
	
	public Booking getBooking(long customerMobile) {
		return bookingRepository.findByCustomerMobile(customerMobile);
	}
	
	public List<Booking> getBookings(String userName) {
		return bookingRepository.findByUserName(userName);
	}
	
	public Booking updateBookingStatus(int bookingId, String newStatus) {
        Booking booking = bookingRepository.findById(bookingId).orElse(null);
        if (booking != null) {
            booking.setStatus(newStatus);
            return bookingRepository.save(booking);
        }
        return null;
    }
        
}
	

