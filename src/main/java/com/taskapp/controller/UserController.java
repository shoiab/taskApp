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
import com.taskapp.utils.Md5Encryptor;

@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private Md5Encryptor encoder;
	
	@Autowired
	DataService dataservice;
	
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public @ResponseBody HttpStatus createUser(@RequestBody UserModel usermodel) throws NoSuchAlgorithmException {
		dataservice.saveUser(usermodel);
		return HttpStatus.OK;
	}
	
	@RequestMapping(value = "/auth", method = RequestMethod.POST)
	public @ResponseBody JSONObject authenticate(@RequestBody UserModel usermodel) throws NoSuchAlgorithmException {
		/*UserModel model = userrepo.findByEmail(usermodel.getEmail());
		System.out.println("password :: "+model.getEmail() + " , " + model.getPassword());
		
		if(encoder.encryptPassword(usermodel.getPassword()).equals(model.getPassword())){
			System.out.println("Authenticated");
		}*/
		
		return null;
		
		
	}

}
