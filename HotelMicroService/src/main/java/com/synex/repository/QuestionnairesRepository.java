package com.synex.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.synex.domain.Questionnaires;

@Repository
public interface QuestionnairesRepository extends JpaRepository<Questionnaires, Integer>{
	
	List<Questionnaires> findByHotelId(int hotelId);
	
	List<Questionnaires> findByUserName(String userName);
	
	 //@Query("SELECT q FROM Questionnaires q WHERE q.adminAnswer IS NULL ORDER BY q.dateAsked DESC")
	 //List<Questionnaires> findRecentQuestionsForAdmin();

	//List<Questionnaires> findByAdminAnswerIsNull();
}
