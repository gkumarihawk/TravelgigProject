package com.synex.controller;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.synex.domain.Guest;
import com.synex.service.GuestService;

@RestController
public class GuestController {
	
	@Autowired GuestService guestService;
	
	@CrossOrigin
	@RequestMapping(value="/saveGuest")
	public Guest saveGuest(@RequestBody Guest guest ) {
		return guestService.save(guest);
	}
	
	@CrossOrigin
	@RequestMapping(value="/findAllGuests")
	public Set<Guest> findAllGuests(){
		return guestService.findAll();
	}
	
	@CrossOrigin
	@RequestMapping(value="/findGuestsByMobile/{phoneNumber}")
	public Set<Guest> findByMobile(@RequestBody long phoneNumber){
		return guestService.findByPhoneNumber(phoneNumber);
	}
	
	/*
	 * @RequestMapping(value = "/saveAttendance", method = RequestMethod.POST)
	public Attendance saveAttendance(@RequestBody Attendance attendance) {
		
		return attendanceService.saveAttendance(attendance);
		
	}
	
	@RequestMapping(value = "/findByDate/{date}", method = RequestMethod.GET)
	public List<Attendance> findByDate(@PathVariable String date){
		List<Attendance> attend = attendanceService.findByDate(date);
		return attend;
	}*/
	 
	
	

}
