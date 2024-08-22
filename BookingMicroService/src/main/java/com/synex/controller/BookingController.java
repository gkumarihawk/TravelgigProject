package com.synex.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.synex.domain.Booking;
import com.synex.service.BookingService;

@RestController
public class BookingController {
	
	@Autowired BookingService bookingService;
	
	@CrossOrigin
	@RequestMapping(value="/saveBooking")
	public Booking save(@RequestBody Booking booking) {
		return bookingService.save(booking);
	}
	
	@CrossOrigin
	@RequestMapping(value="/findBookingByMobile/{customerMobile}")
	public Booking findByMobile(@PathVariable long customerMobile) {
		return bookingService.getBooking(customerMobile);
	}
	
	@CrossOrigin
	@RequestMapping(value="/findBookingByUserName/{userName}")
	public List<Booking> findByUserName(@PathVariable String userName) {
		return bookingService.getBookings(userName);
	}
	
	@CrossOrigin
	@PutMapping("/updateBookingStatus/{bookingId}")
    public Booking updateBookingStatus(@PathVariable int bookingId, @RequestBody Booking updatedBooking) {
        return bookingService.updateBookingStatus(bookingId, updatedBooking.getStatus());
    }

}
