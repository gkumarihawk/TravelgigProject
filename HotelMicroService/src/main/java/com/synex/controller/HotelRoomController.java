package com.synex.controller;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.synex.services.HotelRoomService;

@RestController
public class HotelRoomController {
	
	@Autowired
    private HotelRoomService hotelRoomService;
	

	

	@GetMapping("/roomTypes/{hotelId}")
    public Set<String> getRoomTypesByHotelId(@PathVariable int hotelId) {
        return hotelRoomService.getRoomTypesByHotelId(hotelId);
    }
	
	@CrossOrigin
	@GetMapping("/hotelRoom/getHotelRoomId")
	public int getHotelRoomId(@RequestParam String roomType) {
	    return hotelRoomService.getHotelRoomIdByRoomType(roomType);
	}

}
