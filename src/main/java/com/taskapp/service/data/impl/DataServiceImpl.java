package com.taskapp.service.data.impl;

import java.security.NoSuchAlgorithmException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taskapp.dbOperation.DbOperationService;
import com.taskapp.model.UserModel;
import com.taskapp.service.data.DataService;
import com.taskapp.utils.Md5Encryptor;

@Service
public class DataServiceImpl implements DataService{
	
	@Autowired
	public Md5Encryptor encrypter;
	
	@Autowired
	public DbOperationService dbservice;

	@Override
	public void saveUser(UserModel usermodel) throws NoSuchAlgorithmException {
		System.out.println("usermodel ::" +usermodel.getEmail() + usermodel.getPassword());
		String encryptUserPassword = encrypter.encryptPassword(usermodel.getPassword());
		usermodel.setPassword(encryptUserPassword);
		dbservice.saveUser(usermodel);
	}

}
