package com.synex.services;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.synex.domain.Hotel;
import com.synex.domain.Questionnaires;
import com.synex.repository.QuestionnairesRepository;

@Service
public class QuestionnairesService {
	
	@Autowired QuestionnairesRepository questionnairesRepository;
	
	public Questionnaires save(Questionnaires questionnaires) {
		return questionnairesRepository.save(questionnaires);
	}
	
	public List<Questionnaires> findByHotelId(int hotelId){
		return questionnairesRepository.findByHotelId(hotelId);
	}
	
	public List<Questionnaires> findByUserName(String userName){
		return questionnairesRepository.findByUserName(userName);
	}
	
	public Questionnaires findById(int id) {
		Optional<Questionnaires> optQuestionnaires = questionnairesRepository.findById(id);
		if(optQuestionnaires.isPresent()) {
		return optQuestionnaires.get();
		}
		return null;
	}
	
	public List<Questionnaires> findAll(){
		return questionnairesRepository.findAll();
	}
	
	/*
	 * public List<Questionnaires> findRecentQuestionsForAdmin() { // Fetch all
	 * questionnaires List<Questionnaires> allQuestionnaires =
	 * questionnairesRepository.findAll();
	 * 
	 * // Filter the questionnaires with adminAnswer null and sort by dateAsked
	 * List<Questionnaires> recentQuestionsForAdmin = allQuestionnaires.stream()
	 * .filter(questionnaire -> questionnaire.getAdminAnswer() == null)
	 * .sorted(Comparator.comparing(Questionnaires::getDateAsked).reversed())
	 * .collect(Collectors.toList());
	 * 
	 * return recentQuestionsForAdmin; }
	 */
/*
 * public List<Questionnaires> findUnansweredQuestions() { return
 * questionnairesRepository.findByAdminAnswerIsNull(); } }
 */
}