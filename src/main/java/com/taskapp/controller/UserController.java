package com.taskapp.controller;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.apache.solr.client.solrj.SolrServerException;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.taskapp.model.TaskModel;
import com.taskapp.model.UserModel;
import com.taskapp.service.data.DataService;

@RestController
public class UserController {

	@Autowired
	DataService dataservice;

	@Inject
	private RestTemplate restTemplate;
	
	/*@RequestMapping(value = "/createUser", method = RequestMethod.POST)
	public @ResponseBody JSONObject createUser(@RequestBody UserModel usermodel)
			throws NoSuchAlgorithmException, SolrServerException, IOException {
		usermodel.setDateOfCreation(new Date());
		HttpStatus status = dataservice.saveUser(usermodel);
		
		JSONObject statusobj = new JSONObject();
		statusobj.put("status", status.value());
		if (status == HttpStatus.FOUND) {
			statusobj.put("message", "User already exists");
		} else {
			statusobj.put("message", "User registered successfully");
		}

		return statusobj;

	}*/
	
	
	@RequestMapping(value = "/createUser", method = RequestMethod.POST)
	public @ResponseBody JSONObject createUser(@RequestBody UserModel usermodel)
			throws NoSuchAlgorithmException, SolrServerException, IOException, URISyntaxException {
			JSONObject statusobj = new JSONObject();
			URI url = new URI("http://localhost:8083/api/taskapp/createUser");

			statusobj = restTemplate.postForObject(url, usermodel,
					JSONObject.class);
		return statusobj;
		
	}

	/*@RequestMapping(value = "/auth", method = RequestMethod.POST)
	public @ResponseBody JSONObject authenticate(
			@RequestParam(value = "email") String email,
			@RequestParam(value = "password") String password)
			throws NoSuchAlgorithmException, ParseException {
		JSONObject userobj = new JSONObject();
		userobj.put("email", email);
		userobj.put("password", password);
		return dataservice.authenticate(userobj);
	}*/
	
	@RequestMapping(value = "/auth", method = RequestMethod.GET)
	public @ResponseBody JSONObject authenticate(
			@RequestHeader(value = "email") String email,
			@RequestHeader(value = "password") String password)
			throws NoSuchAlgorithmException, ParseException, URISyntaxException {
	JSONObject statusobj = new JSONObject();
	MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
	map.add("email", email);
	map.add("password", password);
	URI url = new URI("http://localhost:8082/api/taskapp/auth");
	statusobj = restTemplate.postForObject(url, map, JSONObject.class);
		return statusobj;
	}

	/*@RequestMapping(value = "/changePassword", method = RequestMethod.POST)
	public @ResponseBody JSONObject changePassword(
			@RequestHeader(value = "auth_key") String auth_key,
			@RequestParam(value = "email") String email,
			@RequestParam(value = "newPassword") String newPassword)
			throws NoSuchAlgorithmException, ParseException {
		UserModel usermodel = new UserModel();
		usermodel.setEmail(email);
		usermodel.setPassword(newPassword);
		return dataservice.changePassword(usermodel, auth_key);
	}*/
	
	@RequestMapping(value = "/changePassword", method = RequestMethod.POST)
	public @ResponseBody JSONObject changePassword(
			@RequestHeader(value = "auth_key") String auth_key,
			@RequestParam(value = "email") String email,
			@RequestParam(value = "newPassword") String newPassword)
			throws NoSuchAlgorithmException, ParseException, URISyntaxException {
		JSONObject statusobj = new JSONObject();
		URI url = new URI("http://localhost:8083/api/taskapp/changePassword");
		JSONObject paramjson = new JSONObject();
		paramjson.put("auth_key", auth_key);
		paramjson.put("email", email);
		paramjson.put("newPassword", newPassword);
		statusobj = restTemplate.postForObject(url, paramjson,
				JSONObject.class);
	return statusobj;
	}

	@RequestMapping(value = "/getuserfromcache", method = RequestMethod.POST)
	public @ResponseBody JSONObject getUserFromCache(
			@RequestHeader(value = "auth_key") String auth_key)
			throws NoSuchAlgorithmException {
		return dataservice.getUser(auth_key);
	}
	
	/*@RequestMapping(value = "/getMyTasks", method = RequestMethod.POST)
	public @ResponseBody List<TaskModel> getMyTasks(
			@RequestHeader(value = "auth_key") String auth_key)
			throws NoSuchAlgorithmException {
		return dataservice.getMyTasks(auth_key);
	}*/
}
