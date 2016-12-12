package com.taskapp.service.data;

import java.security.NoSuchAlgorithmException;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import com.taskapp.model.UserModel;

public interface DataService {
	public void saveUser(UserModel usermodel) throws NoSuchAlgorithmException;

	public JSONObject authenticate(JSONObject userobj) throws ParseException;
	
	public JSONObject getUser( final String key );
	
}
