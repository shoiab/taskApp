package com.taskapp.dbOperation;

import org.json.simple.JSONObject;

import com.taskapp.model.UserModel;

public interface DbOperationService {
	public void saveUser(UserModel usermodel);

	public UserModel getUserObj(String email);

}
