package com.synex.repository;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.synex.domain.RoomType;

@Repository
public interface RoomTypeRepository extends JpaRepository<RoomType, Integer>{

	
	/*@Query("SELECT rt FROM RoomType rt JOIN rt.hotelRoom hr WHERE hr.hotel.hotelId = :hotelId")
    List<RoomType> findByHotelId(int hotelId);*/


	
	
}
