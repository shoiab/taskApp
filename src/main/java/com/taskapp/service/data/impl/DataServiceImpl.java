package com.taskapp.service.data.impl;

import java.security.NoSuchAlgorithmException;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taskapp.dbOperation.DbOperationService;
import com.taskapp.model.UserModel;
import com.taskapp.service.data.DataService;
import com.taskapp.utils.Encrypt;

@Service
public class DataServiceImpl implements DataService{
	
	@Autowired
	public DbOperationService dbservice;
	
	@Autowired
	public Encrypt encryptor;

	@Override
	public void saveUser(UserModel usermodel) throws NoSuchAlgorithmException {
		String encryptUserPassword = encryptor.textEncrypt(usermodel.getPassword());
		usermodel.setPassword(encryptUserPassword);
		dbservice.saveUser(usermodel);
	}

	@Override
	public boolean authenticate(JSONObject auth) {
		UserModel user = dbservice.getUserObj(auth.get("email").toString());
		return encryptor.compareWithEncryptText(auth.get("password").toString(), user.getPassword());
		
	}

}
