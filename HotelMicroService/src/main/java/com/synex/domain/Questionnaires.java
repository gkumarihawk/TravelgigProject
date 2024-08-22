package com.synex.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.Date;

@Entity
@Table(name="questionnaires")
public class Questionnaires {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    private int hotelId;
    private String userName;
    
    private String question;
    private String adminAnswer;
    private Date dateAsked;
    
    public Questionnaires() {
        
    }
    
    
    public Questionnaires(int hotelId, String userName, String question, String adminAnswer, Date dateAsked) {
        this.hotelId = hotelId;
        this.userName = userName;
        this.question = question;
        this.adminAnswer = adminAnswer;
        this.dateAsked = dateAsked;
        
    }
    
    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getHotelId() {
        return hotelId;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAdminAnswer() {
        return adminAnswer;
    }

    public void setAdminAnswer(String adminAnswer) {
        this.adminAnswer = adminAnswer;
    }

    public Date getDateAsked() {
        return dateAsked;
    }

    public void setDateAsked(Date dateAsked) {
        this.dateAsked = dateAsked;
    }

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setHotelId(int hotelId) {
		this.hotelId = hotelId;
	}
    
    
}
