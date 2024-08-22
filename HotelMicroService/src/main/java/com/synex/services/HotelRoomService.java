package com.synex.services;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.synex.repository.HotelRepository;
import com.synex.repository.HotelRoomRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

@Service
public class HotelRoomService {
	
	@Autowired
    private HotelRepository hotelRepository;
	
	@Autowired HotelRoomRepository hotelRoomRepository;
    
    @Autowired
    private EntityManagerFactory entityManagerFactory;

    public Set<String> getRoomTypesByHotelId(int hotelId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            return hotelRepository.getRoomTypesByHotelId(hotelId, entityManager);
        } finally {
            entityManager.close();
        }
    }
    
    public int getHotelRoomIdByRoomType(String roomType) {
        return hotelRoomRepository.findHotelRoomIdByRoomType(roomType);
    }

}
