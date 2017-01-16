package com.taskapp.controller;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.NoSuchAlgorithmException;

import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.apache.solr.client.solrj.SolrServerException;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
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
import org.springframework.web.util.UriComponentsBuilder;

import com.taskapp.model.UserModel;

@RestController
public class UserController {
	
	static Logger logger = Logger.getLogger(UserController.class.getName());

	@Inject
	private RestTemplate restTemplate;
	
	@Autowired
	private MessageSource messageSource;

	/*
	 * @RequestMapping(value = "/createUser", method = RequestMethod.POST)
	 * public @ResponseBody JSONObject createUser(@RequestBody UserModel
	 * usermodel) throws NoSuchAlgorithmException, SolrServerException,
	 * IOException { usermodel.setDateOfCreation(new Date()); HttpStatus status
	 * = dataservice.saveUser(usermodel);
	 * 
	 * JSONObject statusobj = new JSONObject(); statusobj.put("status",
	 * status.value()); if (status == HttpStatus.FOUND) {
	 * statusobj.put("message", "User already exists"); } else {
	 * statusobj.put("message", "User registered successfully"); }
	 * 
	 * return statusobj;
	 * 
	 * }
	 */

	@RequestMapping(value = "/createUser", method = RequestMethod.POST)
	public @ResponseBody JSONObject createUser(@RequestBody UserModel usermodel)
			throws NoSuchAlgorithmException, SolrServerException, IOException,
			URISyntaxException {
		JSONObject statusobj = new JSONObject();
		URI url = new URI("http://localhost:8083/api/user/createUser");

		statusobj = restTemplate
				.postForObject(url, usermodel, JSONObject.class);
		return statusobj;

	}

	/*
	 * @RequestMapping(value = "/auth", method = RequestMethod.POST) public
	 * @ResponseBody JSONObject authenticate(
	 * 
	 * @RequestParam(value = "email") String email,
	 * 
	 * @RequestParam(value = "password") String password) throws
	 * NoSuchAlgorithmException, ParseException { JSONObject userobj = new
	 * JSONObject(); userobj.put("email", email); userobj.put("password",
	 * password); return dataservice.authenticate(userobj); }
	 */

	@RequestMapping(value = "/auth", method = RequestMethod.POST)
	public @ResponseBody JSONObject authenticate(
			@RequestParam(value = "email") String email,
			@RequestParam(value = "password") String password)
			throws NoSuchAlgorithmException, ParseException, URISyntaxException {
		
	    String url = "http://localhost:8082/api/authenticate/auth";
		
		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url)
		        // Add query parameter
		        .queryParam("email", email)
		        .queryParam("password", password);
		
		logger.info("statusobj :: "+builder.build().toUri());
		JSONObject statusobj = new JSONObject();
		
		statusobj = restTemplate
				.postForObject(builder.build().toUri(), null, JSONObject.class);
		
		return statusobj;		

	}

	/*
	 * @RequestMapping(value = "/changePassword", method = RequestMethod.POST)
	 * public @ResponseBody JSONObject changePassword(
	 * 
	 * @RequestHeader(value = "auth_key") String auth_key,
	 * 
	 * @RequestParam(value = "email") String email,
	 * 
	 * @RequestParam(value = "newPassword") String newPassword) throws
	 * NoSuchAlgorithmException, ParseException { UserModel usermodel = new
	 * UserModel(); usermodel.setEmail(email);
	 * usermodel.setPassword(newPassword); return
	 * dataservice.changePassword(usermodel, auth_key); }
	 */

	@RequestMapping(value = "/changePassword", method = RequestMethod.POST)
	public @ResponseBody JSONObject changePassword(
			@RequestHeader(value = "auth_key") String auth_key,
			@RequestParam(value = "email") String email,
			@RequestParam(value = "newPassword") String newPassword)
			throws NoSuchAlgorithmException, ParseException, URISyntaxException {
		
		//URI url = new URI("http://localhost:8083/api/taskapp/changePassword");
		
		String url = "http://localhost:8083/api/user/changePassword";
		
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
		headers.add("auth_key", auth_key);
		
		restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

		HttpEntity<String> request = new HttpEntity<String>(headers);
		
		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url)
		        // Add query parameter
		        .queryParam("email", email)
		        .queryParam("newPassword", newPassword);
		
		
		logger.info("statusobj :: "+builder.build().toUri());
		JSONObject statusobj = new JSONObject();
		
		statusobj = restTemplate
				.postForObject(builder.build().toUri(), request, JSONObject.class);
		
		return statusobj;
	}

	@RequestMapping(value = "/getuserfromcache", method = RequestMethod.POST)
	public @ResponseBody JSONObject getUserFromCache(
			@RequestHeader(value = "auth_key") String auth_key)
			throws NoSuchAlgorithmException, URISyntaxException {
		
		URI url = new URI("http://localhost:8083/api/user/getuserfromcache");
		
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
		headers.add("auth_key", auth_key);
		
		HttpEntity<String> request = new HttpEntity<String>(headers);
		JSONObject statusobj = new JSONObject();
		
		statusobj = restTemplate
				.postForObject(url, request, JSONObject.class);
		
		return statusobj;
		
	}

	/*
	 * @RequestMapping(value = "/getMyTasks", method = RequestMethod.POST)
	 * public @ResponseBody List<TaskModel> getMyTasks(
	 * 
	 * @RequestHeader(value = "auth_key") String auth_key) throws
	 * NoSuchAlgorithmException { return dataservice.getMyTasks(auth_key); }
	 */
	
	/*@RequestMapping("/msg")
	   public String msg(@RequestHeader("Accept-Language") Locale locale){
	      return messageSource.getMessage("msg",null,locale);
	   }*/
}
