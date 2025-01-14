package com.synex.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.synex.client.HotelClient;

@RestController
public class HotelController {
	
	
	@Autowired HotelClient hotelClient;
	
	@RequestMapping(value="/findHotel/{searchString}")
	public JsonNode searchHotel(@PathVariable String searchString) {
		
			return hotelClient.searchHotel(searchString);
		
	}
	
	@RequestMapping(value="/hotel/findHotel/{id}")
	public JsonNode findHotel(@PathVariable int id) {
		return hotelClient.findHotel(id);
	}

}
