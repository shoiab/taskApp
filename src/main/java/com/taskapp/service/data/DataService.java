package com.taskapp.service.data;

import java.security.NoSuchAlgorithmException;

import org.json.simple.JSONObject;

import com.taskapp.model.UserModel;

public interface DataService {
	public void saveUser(UserModel usermodel) throws NoSuchAlgorithmException;

	public boolean authenticate(JSONObject auth);
	
	public UserModel getUser( final String key );
	
	public void setUser( final UserModel usermodel);
}
