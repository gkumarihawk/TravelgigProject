package com.synex.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class GatewayController {
	
	@RequestMapping(value={"/home", "/"}, method=RequestMethod.GET)
	public String home() {
		return "home";
	}
	
	@RequestMapping(value="/userProfile")
	public String bookings() {
		return "userProfile";
	}

}
