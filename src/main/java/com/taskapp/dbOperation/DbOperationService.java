package com.taskapp.dbOperation;


import org.json.simple.JSONObject;
import org.springframework.http.HttpStatus;

import com.taskapp.model.GroupModel;
import com.taskapp.model.UserModel;

public interface DbOperationService {
	public JSONObject saveUser(UserModel usermodel);

	public UserModel getUserObj(String email);

	public void createTag(String name, String tagTypeUser, String email);

	public void updateUserPassword(String encryptUserPassword, String email);

	public JSONObject createGroup(GroupModel groupmodel);

}
