package com.taskapp.controller;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrDocumentList;
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
	public @ResponseBody HttpStatus createUser(@RequestBody UserModel usermodel)
			throws NoSuchAlgorithmException, SolrServerException, IOException {
		return dataservice.saveUser(usermodel);
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

	@RequestMapping(value = "/getuserfromcache", method = RequestMethod.POST)
	public @ResponseBody JSONObject getUserFromCache(
			@RequestHeader(value = "key") String auth_key)
			throws NoSuchAlgorithmException {
		return dataservice.getUser(auth_key);
	}

	@RequestMapping(value = "/tags", method = RequestMethod.POST)
	public void addTag(@RequestParam(value = "tagName") String tagName,
			@RequestParam(value = "tagType") String tagType,
			@RequestParam(value = "tagValue") String tagValue)
			throws NoSuchAlgorithmException, SolrServerException, IOException {
		dataservice.createTag(tagName, tagType, tagValue);
		
	}

	@RequestMapping(value = "/tags", method = RequestMethod.GET)
	public @ResponseBody SolrDocumentList getindex(
			@RequestParam(value = "search") String searchVal)
			throws NoSuchAlgorithmException, SolrServerException, IOException {
		
		return dataservice.fetchTag(searchVal);

	}

	@RequestMapping(value = "/tags", method = RequestMethod.DELETE)
	public void deleteindex(
			@RequestParam(value = "fieldName") String fieldName,
			@RequestParam(value = "fieldValue") String fieldValue)
			throws NoSuchAlgorithmException, SolrServerException, IOException {
		dataservice.deleteTag(fieldName, fieldValue);
	}
	
	
	
	/*@RequestMapping(value = "/createTagType", method = Req)*/

}
