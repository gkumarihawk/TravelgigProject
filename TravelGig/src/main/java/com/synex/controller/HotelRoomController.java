package com.synex.controller;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.synex.client.HotelRoomClient;

@RestController
public class HotelRoomController {
	
	@Autowired HotelRoomClient hotelRoomClient;
	
	@GetMapping("/hotelRoom/roomTypes/{hotelId}")
    public JsonNode getRoomTypesByHotelId(@PathVariable int hotelId) {
        return hotelRoomClient.getRoomTypes(hotelId);
    }

}
