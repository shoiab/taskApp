package com.taskapp.dbOperation;


import java.util.List;

import org.json.simple.JSONObject;

import com.taskapp.model.GroupModel;
import com.taskapp.model.TaskModel;
import com.taskapp.model.UserModel;

public interface DbOperationService {
	public JSONObject saveUser(UserModel usermodel);

	public UserModel getUserObj(String email);

	public void createTag(String name, String tagTypeUser, String email);

	public void updateUserPassword(String encryptUserPassword, String email);

	public JSONObject createGroup(GroupModel groupmodel);

	public JSONObject createTask(TaskModel taskModel);

	public TaskModel fetchTask(String taskName);

	public List<TaskModel> getMyTasks(String userEmail);

}
