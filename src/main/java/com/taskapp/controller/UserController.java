package com.taskapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/finally")
public class UserController {
	
	@RequestMapping(value = "/retrieve", method = RequestMethod.GET)
	public @ResponseBody String printHelloWorld() {
		System.out.println("hello pls print");
		return "Hello World";
	}
	
	@RequestMapping(value = "/find", method = RequestMethod.GET)
	public @ResponseBody String printSomething() {
		System.out.println("hello pls print for find");
		return "What the heck man!!";
	}

}
