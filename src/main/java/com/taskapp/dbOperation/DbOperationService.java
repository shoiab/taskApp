package com.taskapp.dbOperation;


import org.json.simple.JSONObject;

import com.taskapp.model.GroupModel;
import com.taskapp.model.TaskModel;

public interface DbOperationService {
	//public JSONObject saveUser(UserModel usermodel);

	//public UserModel getUserObj(String email);

	public void createTag(String name, String tagTypeUser, String email);

	//public void updateUserPassword(String encryptUserPassword, String email);

	public JSONObject createGroup(GroupModel groupmodel);

	public JSONObject createTask(TaskModel taskModel);

	public TaskModel fetchTask(String taskName);

	public void createTaskTag(TaskModel taskModel);

	/*public List<TaskModel> getMyTasks(String userEmail);*/

}
