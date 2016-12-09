package com.taskapp.controller;

import java.security.NoSuchAlgorithmException;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.taskapp.model.UserModel;
import com.taskapp.service.data.DataService;

@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	DataService dataservice;
	
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public @ResponseBody HttpStatus createUser(@RequestBody UserModel usermodel) throws NoSuchAlgorithmException {
		dataservice.saveUser(usermodel);
		return HttpStatus.OK;
	}
	
	@RequestMapping(value = "/auth", method = RequestMethod.POST)
	public @ResponseBody boolean authenticate(@RequestBody JSONObject auth) throws NoSuchAlgorithmException {
		return dataservice.authenticate(auth);
	}

}
