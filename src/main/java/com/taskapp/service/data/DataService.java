package com.taskapp.service.data;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import org.apache.solr.client.solrj.SolrServerException;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.springframework.http.HttpStatus;

import com.taskapp.model.TaskModel;
import com.taskapp.model.UserModel;

public interface DataService {
	public HttpStatus saveUser(UserModel usermodel) throws NoSuchAlgorithmException, SolrServerException, IOException;

	public JSONObject authenticate(JSONObject userobj) throws ParseException;
	
	public JSONObject getUser( final String key );

	public JSONObject changePassword(UserModel usermodel, String auth_key);

	public String getUserEmail(String auth_key);

	public List<TaskModel> getMyTasks(String auth_key);
	
}
