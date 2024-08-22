package com.synex.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.synex.domain.Hotel;
import com.synex.services.HotelService;

@RestController
public class HotelController {

	@Autowired HotelService hotelService;
	
	@RequestMapping(value="/searchHotel/{searchString}", method = RequestMethod.GET)
	public List<Hotel> searchHotel(@PathVariable String searchString){
		return hotelService.searchHotel(searchString);
	}
	
	@RequestMapping(value="/findHotel/{id}", method = RequestMethod.GET)
	public Hotel findHotel(@PathVariable int id) {
		return hotelService.findById(id);
	}
}