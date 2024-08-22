package com.synex.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.synex.client.RoomTypeClient;

@RestController
public class RoomTypeController {
	
	@Autowired RoomTypeClient roomTypeClient;
	
	@RequestMapping("/roomTypes/getAll")
	public JsonNode allTheRoomTypes() {
		return roomTypeClient.getRoomTypes();
		
	}
	

}
