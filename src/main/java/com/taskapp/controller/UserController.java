package com.taskapp.controller;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.taskapp.model.UserModel;
import com.taskapp.repository.UserMongoRepository;

@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	UserMongoRepository userrepo;
	
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public @ResponseBody JSONObject createUser(@RequestBody UserModel usermodel) {
		System.out.println("hello pls print");
		userrepo.save(usermodel);
		return null;
	}
	
	@RequestMapping(value = "/find", method = RequestMethod.GET)
	public @ResponseBody String printSomething() {
		System.out.println("hello pls print for find");
		return "What the heck man!!";
	}

}
