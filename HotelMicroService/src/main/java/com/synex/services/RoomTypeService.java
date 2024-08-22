package com.synex.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.synex.domain.RoomType;
import com.synex.repository.RoomTypeRepository;

@Service
public class RoomTypeService {
	
	@Autowired RoomTypeRepository roomTypeRepository;
	
    /*public List<RoomType> getRoomTypesByHotelId(int hotelId) {
        return roomTypeRepository.findByHotelId(hotelId);
    }*/
    

}
