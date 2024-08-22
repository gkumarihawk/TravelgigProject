package com.synex.services;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.synex.domain.Hotel;
import com.synex.domain.HotelRoom;
import com.synex.domain.RoomType;
import com.synex.repository.HotelRepository;
import com.synex.repository.HotelRoomRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.transaction.Transactional;

@Service
public class HotelService {
	
	
	@Autowired HotelRepository hotelRepository;
	
	@Autowired
    private HotelRoomRepository hotelRoomRepository;

	public List<Hotel> searchHotel(String searchString){
		
		return hotelRepository.findByHotelNameLikeOrAddressLikeOrCityLike("%"+searchString+"%", "%"+searchString+"%", "%"+searchString+"%");
		
		
	}
	
	public Hotel findById(int id) {
		Optional<Hotel> optHotel = hotelRepository.findById(id);
		if(optHotel.isPresent()) {
		return optHotel.get();
		}
		return null;
	}
	
}
