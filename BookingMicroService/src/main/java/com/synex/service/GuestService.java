package com.synex.service;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.synex.domain.Guest;
import com.synex.repository.GuestRepository;

@Service
public class GuestService {
	
	@Autowired GuestRepository guestRepository;
	
	public Guest save(Guest guest) {
		
		return guestRepository.save(guest);
		
	}
	
	public Set<Guest> findAll(){
		return (Set<Guest>) guestRepository.findAll();
	}
	
	public Set<Guest> findByPhoneNumber(long phoneNumber){
		return guestRepository.findByPhoneNumber(phoneNumber);
	}

}
