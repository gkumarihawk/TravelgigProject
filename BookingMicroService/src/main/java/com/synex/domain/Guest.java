package com.synex.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Entity
@Table(name="guest")
public class Guest {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int 	guestId;
	
	private long phoneNumber;

	private String 	firstName;
	private String 	lastName;
	private String 	gender;
	private int 	age;
	
	public Guest() {}
	
	public Guest(String firstName, String lastName, long phoneNumber, String gender, int age) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.gender = gender;
		this.age = age;
	}

	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public long getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(long phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	
	@Override
	public String toString() {
		return "Guest [guestId=" + guestId + ", firstName=" + firstName + ", lastName="
				+ lastName + ", gender=" + gender + ", age=" + age + "]";
	}
	
	
}
