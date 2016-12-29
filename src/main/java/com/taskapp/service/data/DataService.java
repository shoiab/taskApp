package com.taskapp.service.data;

import com.taskapp.model.TaskModel;

public interface DataService {
	//public HttpStatus saveUser(UserModel usermodel) throws NoSuchAlgorithmException, SolrServerException, IOException;

	//public JSONObject authenticate(JSONObject userobj) throws ParseException;
	
	//public JSONObject getUser( final String key );

	//public JSONObject changePassword(UserModel usermodel, String auth_key);

	public String getUserEmail(String auth_key);

	public void createTaskTag(TaskModel taskModel);

	/*public List<TaskModel> getMyTasks(String auth_key);*/
	
}
