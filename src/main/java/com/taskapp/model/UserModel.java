package com.taskapp.model;

import java.util.Date;

public class UserModel{

	private String name;
	private String email;
	private Date dateOfCreation;
	private String password;
	public UserModel(String name, String email) {
		super();
		this.name = name;
		this.email = email;
	}
	public UserModel() {
		
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Date getDateOfCreation() {
		return dateOfCreation;
	}
	public void setDateOfCreation(Date dateOfCreation) {
		this.dateOfCreation = dateOfCreation;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
}
