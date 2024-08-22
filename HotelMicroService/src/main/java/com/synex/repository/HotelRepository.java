package com.synex.repository;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.synex.domain.Hotel;
import com.synex.domain.HotelRoom;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Integer>{
	
	public List<Hotel> findByHotelNameLikeOrAddressLikeOrCityLike(String hotelName, String address, String city);


	public default Set<String> getRoomTypesByHotelId(int hotelId, EntityManager entityManager) {
        Hotel hotel = entityManager.find(Hotel.class, hotelId);
        if (hotel != null) {
            Set<String> roomTypes = new HashSet<>();
            for (HotelRoom room : hotel.getHotelRooms()) {
                roomTypes.add(room.getType().getName());
            }
            return roomTypes;
        }
        return Collections.emptySet();
    }

}