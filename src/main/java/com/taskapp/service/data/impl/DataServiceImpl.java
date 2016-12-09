package com.taskapp.service.data.impl;

import java.security.NoSuchAlgorithmException;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.taskapp.dbOperation.DbOperationService;
import com.taskapp.model.UserModel;
import com.taskapp.service.data.DataService;

@Service
public class DataServiceImpl implements DataService{
	
	@Autowired
	public DbOperationService dbservice;

	@Override
	public void saveUser(UserModel usermodel) throws NoSuchAlgorithmException {
		System.out.println("usermodel ::" +usermodel.getEmail() + usermodel.getPassword());
		String encryptUserPassword = new BCryptPasswordEncoder().encode(usermodel.getPassword());
		usermodel.setPassword(encryptUserPassword);
		dbservice.saveUser(usermodel);
	}

	@Override
	public boolean authenticate(JSONObject auth) {
		String encryptUserPassword = new BCryptPasswordEncoder().encode(auth.get("password").toString());
		JSONObject json = new JSONObject();
		json.put("email", auth.get("email"));
		json.put("password", auth.get(encryptUserPassword));
		
		return dbservice.authenticate(json);
	}

}
