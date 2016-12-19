package com.taskapp.controller;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

import org.apache.solr.client.solrj.SolrServerException;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.taskapp.model.UserModel;
import com.taskapp.service.data.DataService;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	DataService dataservice;

	@RequestMapping(value = "/createUser", method = RequestMethod.POST)
	public @ResponseBody HttpStatus createUser(@RequestBody UserModel usermodel
			/*@RequestParam(value = "name") String name,
			@RequestParam(value = "email") String email,
			@RequestParam(value = "password") String password*/)
			throws NoSuchAlgorithmException, SolrServerException, IOException {
		UserModel userobj = new UserModel();
		userobj.setName(usermodel.getName());
		userobj.setEmail(usermodel.getEmail());
		userobj.setPassword(usermodel.getPassword());
		userobj.setDateOfCreation(new Date());
		return dataservice.saveUser(userobj);
	}

	@RequestMapping(value = "/auth", method = RequestMethod.POST)
	public @ResponseBody JSONObject authenticate(
			@RequestParam(value = "email") String email,
			@RequestParam(value = "password") String password)
			throws NoSuchAlgorithmException, ParseException {
		JSONObject userobj = new JSONObject();
		userobj.put("email", email);
		userobj.put("password", password);
		return dataservice.authenticate(userobj);
	}
	
	@RequestMapping(value = "/changePassword", method = RequestMethod.POST)
	public @ResponseBody JSONObject changePassword(
			@RequestHeader(value = "auth_key") String auth_key,
			@RequestParam(value = "email") String email,
			@RequestParam (value = "newPassword") String newPassword)
			throws NoSuchAlgorithmException, ParseException {
		UserModel usermodel = new UserModel();
		usermodel.setEmail(email);
		usermodel.setPassword(newPassword);
		return dataservice.changePassword(usermodel, auth_key);
	}

	@RequestMapping(value = "/getuserfromcache", method = RequestMethod.POST)
	public @ResponseBody JSONObject getUserFromCache(
			@RequestHeader(value = "auth_key") String auth_key)
			throws NoSuchAlgorithmException {
		return dataservice.getUser(auth_key);
	}

}
