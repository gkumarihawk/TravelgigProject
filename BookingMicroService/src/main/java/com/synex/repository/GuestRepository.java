package com.synex.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.synex.domain.Guest;

@Repository
public interface GuestRepository extends JpaRepository<Guest, Integer>{
	
	Set<Guest> findByPhoneNumber(long phoneNumber);

}
