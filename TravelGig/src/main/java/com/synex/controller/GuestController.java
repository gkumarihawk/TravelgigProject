package com.synex.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.synex.client.GuestClient;

@RestController
public class GuestController {
	
	@Autowired GuestClient guestClient;
	
	@RequestMapping(value="/guest/saveGuests")
	public JsonNode saveTheGuests(@RequestBody JsonNode node) {
		return guestClient.saveTheGuests(node);
		
	}

}
