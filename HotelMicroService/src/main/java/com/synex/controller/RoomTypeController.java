package com.synex.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.synex.domain.RoomType;
import com.synex.services.RoomTypeService;

@RestController
public class RoomTypeController {
	
	@Autowired RoomTypeService roomTypeService;
	
	/*@GetMapping("/getByHotelId/{hotelId}")
    public List<RoomType> getRoomTypesByHotelId(@PathVariable int hotelId) {
        return roomTypeService.getRoomTypesByHotelId(hotelId);
    }*/

}
