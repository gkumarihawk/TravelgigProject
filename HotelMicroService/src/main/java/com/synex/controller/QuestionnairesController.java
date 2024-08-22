package com.synex.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.synex.domain.Questionnaires;
import com.synex.services.QuestionnairesService;

@RestController
public class QuestionnairesController {
	
	@Autowired QuestionnairesService questionnairesService;
	
	@CrossOrigin
	@RequestMapping("/questionnaires/save")
	public Questionnaires saveQuestionnaires(@RequestBody Questionnaires questionnaires) {
		return questionnairesService.save(questionnaires);
	}
	
	@CrossOrigin
	@RequestMapping("/hotel/questionnaires/{hotelId}")
	public List<Questionnaires> findByHotelId(@PathVariable int hotelId) {
		return questionnairesService.findByHotelId(hotelId);
	}
	
	@CrossOrigin
	@RequestMapping("/user/questionnaires/{userName}")
	public List<Questionnaires> findByUserId(@PathVariable String userName) {
		return questionnairesService.findByUserName(userName);
	}
	
	@CrossOrigin
	@RequestMapping("/questionnaires/{id}")
	public Questionnaires findById(@PathVariable int id) {
		return questionnairesService.findById(id);
	}
	
	@CrossOrigin
	@RequestMapping("/questionnaires")
	public List<Questionnaires> findAll(){
		return questionnairesService.findAll();
	}
	
	@CrossOrigin
	@RequestMapping("/allQuestions")
	public List<Questionnaires> getAllQuestionnaires(){
		return questionnairesService.findAll();
	}
	
	/*
	 * @CrossOrigin
	 * 
	 * @GetMapping("/admin/recent") public List<Questionnaires>
	 * getRecentQuestionsForAdmin() { return
	 * questionnairesService.findRecentQuestionsForAdmin(); }
	 * 
	 * @CrossOrigin
	 * 
	 * @GetMapping("/unanswered") public List<Questionnaires>
	 * getUnansweredQuestions() { return
	 * questionnairesService.findUnansweredQuestions(); }
	 */
	
	@CrossOrigin(origins = "http://localhost:8282", methods = {RequestMethod.PUT})
	@RequestMapping("/updateAnswer/{questionId}")
	public String updateAnswer(@PathVariable int questionId, @RequestBody String answer) {
		Questionnaires question = questionnairesService.findById(questionId);

	    if (question != null) {
	        question.setAdminAnswer(answer);
	        questionnairesService.save(question);
	        return "Answer updated successfully";
	    } else {
	        return "Question not found with ID: " + questionId;
	    }
	}
}
