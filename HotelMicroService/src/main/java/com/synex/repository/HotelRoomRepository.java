package com.synex.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.synex.domain.HotelRoom;

@Repository
public interface HotelRoomRepository extends JpaRepository<HotelRoom, Integer> {
	    
	    @Query("SELECT hr FROM HotelRoom hr JOIN FETCH hr.type")
	    List<HotelRoom> findAllHotelRooms();
	    
	    @Query("SELECT hr.hotelRoomId FROM HotelRoom hr WHERE hr.type.name = :roomType")
	    int findHotelRoomIdByRoomType(@Param("roomType") String roomType);
	}


