package com.taskapp.service.data;

import java.security.NoSuchAlgorithmException;

import com.taskapp.model.UserModel;

public interface DataService {
	public void saveUser(UserModel usermodel) throws NoSuchAlgorithmException;
}
