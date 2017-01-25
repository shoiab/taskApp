package com.taskapp.controller;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.NoSuchAlgorithmException;

import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrDocumentList;
import org.json.simple.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
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

import com.taskapp.model.TaskModel;

@RestController
public class TaskController {
	
	static Logger logger = Logger.getLogger(TaskController.class.getName());

	@Inject
	private RestTemplate restTemplate;

	@RequestMapping(value = "/createNewTask", method = RequestMethod.POST)
	public @ResponseBody JSONObject createTask(
			@RequestHeader(value = "auth_key") String auth_key,
			@RequestBody TaskModel taskModel) throws NoSuchAlgorithmException,
			SolrServerException, IOException, URISyntaxException {

		URI url = new URI("http://localhost:8087/api/task/createNewTask");
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
		headers.add("auth_key", auth_key);
		headers.add("Content-Type", "application/json");

		RestTemplate restTemplate = new RestTemplate();
		restTemplate.getMessageConverters().add(
				new MappingJackson2HttpMessageConverter());

		HttpEntity<TaskModel> request = new HttpEntity<TaskModel>(taskModel,
				headers);
		JSONObject statusobj = new JSONObject();
		statusobj = restTemplate.postForObject(url, request, JSONObject.class);
		return statusobj;

	}

	/*@RequestMapping(value = "/postTask", method = RequestMethod.GET)
	public @ResponseBody JSONObject notifyTask(
			@RequestHeader(value = "auth_key") String auth_key,
			@RequestParam(value = "taskid") String taskid)
			throws URISyntaxException {

		String url = "http://localhost:8087/api/task/postTask";

		MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
		headers.add("auth_key", auth_key);
		restTemplate.getMessageConverters().add(
				new MappingJackson2HttpMessageConverter());

		HttpEntity<String> request = new HttpEntity<String>(headers);

		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url)
		// Add query parameter
				.queryParam("taskid", taskid);

		ResponseEntity<JSONObject> statusobj = restTemplate.exchange(builder
				.build().toUri(), HttpMethod.GET, request, JSONObject.class);

		return statusobj.getBody();
	}*/

	@RequestMapping(value = "/getUserTasks", method = RequestMethod.POST)
	public @ResponseBody SolrDocumentList getMyTasks(
			@RequestHeader(value = "auth_key") String auth_key)
			throws NoSuchAlgorithmException, SolrServerException, IOException {

		String url = "http://localhost:8087/api/task/getUserTasks";

		MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
		headers.add("auth_key", auth_key);

		restTemplate.getMessageConverters().add(
				new MappingJackson2HttpMessageConverter());

		HttpEntity<String> request = new HttpEntity<String>(headers);
		SolrDocumentList statusobj = new SolrDocumentList();

		statusobj = restTemplate.postForObject(url, request,
				SolrDocumentList.class);

		return statusobj;

	}
	
	/*@RequestMapping(value = "/getOpenCreatedTasks", method = RequestMethod.POST)
	public @ResponseBody JSONObject getOpenCreatedTasks(
			@RequestHeader(value = "auth_key") String auth_key) throws NoSuchAlgorithmException,
			SolrServerException, IOException {
		String url = "http://localhost:8087/api/task/getOpenCreatedTasks";

		MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
		headers.add("auth_key", auth_key);

		restTemplate.getMessageConverters().add(
				new MappingJackson2HttpMessageConverter());

		HttpEntity<String> request = new HttpEntity<String>(headers);
		JSONObject statusobj = new JSONObject();

		statusobj = restTemplate.postForObject(url, request,
				JSONObject.class);
		

		return statusobj;
		
	}*/
	
	/*@RequestMapping(value = "/getAssignedTasksForStatus", method = RequestMethod.POST)
	public @ResponseBody JSONObject getAssignedTasksForStatus(
			@RequestHeader(value = "auth_key") String auth_key,
			@RequestHeader(value = "taskStatus") String taskStatus) throws NoSuchAlgorithmException,
			SolrServerException, IOException {
		
		String url = "http://localhost:8087/api/task/getAssignedTasksForStatus";

		MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
		headers.add("auth_key", auth_key);
		headers.add("taskStatus", taskStatus);

		restTemplate.getMessageConverters().add(
				new MappingJackson2HttpMessageConverter());

		HttpEntity<String> request = new HttpEntity<String>(headers);
		JSONObject statusobj = new JSONObject();

		statusobj = restTemplate.postForObject(url, request,
				JSONObject.class);

		return statusobj;
	}*/
	
	/*@RequestMapping(value = "/getTasksForStatus", method = RequestMethod.POST)
	public @ResponseBody JSONObject getMyTasksForStatus(
			@RequestHeader(value = "auth_key") String auth_key,
			@RequestHeader(value = "taskStatus") String taskStatus) throws NoSuchAlgorithmException,
			SolrServerException, IOException {
		
		String url = "http://localhost:8087/api/task/getTasksForStatus";

		MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
		headers.add("auth_key", auth_key);
		headers.add("taskStatus", taskStatus);

		restTemplate.getMessageConverters().add(
				new MappingJackson2HttpMessageConverter());

		HttpEntity<String> request = new HttpEntity<String>(headers);
		JSONObject statusobj = new JSONObject();

		statusobj = restTemplate.postForObject(url, request,
				JSONObject.class);

		return statusobj;
	}*/
	
	/*@RequestMapping(value = "/changeTaskStatus", method = RequestMethod.POST)
	public @ResponseBody JSONObject changeTaskStatus(
			@RequestHeader(value = "auth_key") String auth_key,
			@RequestHeader(value = "taskId") String taskId,
			@RequestHeader(value = "taskStatus") String taskStatus) throws NoSuchAlgorithmException,
			SolrServerException, IOException {
		
		String url = "http://localhost:8087/api/task/changeTaskStatus";

		MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
		headers.add("auth_key", auth_key);
		headers.add("taskStatus", taskStatus);
		headers.add("taskId", taskId);

		restTemplate.getMessageConverters().add(
				new MappingJackson2HttpMessageConverter());

		HttpEntity<String> request = new HttpEntity<String>(headers);
		JSONObject statusobj = new JSONObject();

		statusobj = restTemplate.postForObject(url, request,
				JSONObject.class);

		return statusobj;
	}*/
	
	/*@RequestMapping(value = "/getCompletedTasks", method = RequestMethod.POST)
	public @ResponseBody JSONObject getCompletedTasks(
			@RequestHeader(value = "auth_key") String auth_key) throws NoSuchAlgorithmException,
			SolrServerException, IOException {
		
		String url = "http://localhost:8087/api/task/getCompletedTasks";

		MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
		headers.add("auth_key", auth_key);

		restTemplate.getMessageConverters().add(
				new MappingJackson2HttpMessageConverter());

		HttpEntity<String> request = new HttpEntity<String>(headers);
		JSONObject statusobj = new JSONObject();

		statusobj = restTemplate.postForObject(url, request,
				JSONObject.class);

		return statusobj;
	}*/
	
	@RequestMapping(value = "/getTaskCount/v2", method = RequestMethod.POST)
	public @ResponseBody JSONObject getTasksCountv2(
			@RequestHeader(value = "auth_key") String auth_key) throws NoSuchAlgorithmException,
			SolrServerException, IOException {
		
		String url = "http://localhost:8087/api/task/getTaskCount/v2";

		MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
		headers.add("auth_key", auth_key);
		
		restTemplate.getMessageConverters().add(
				new MappingJackson2HttpMessageConverter());

		HttpEntity<String> request = new HttpEntity<String>(headers);
		//JSONObject statusobj = new JSONObject();
		
		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url);

	/*	statusobj = restTemplate.postForObject(url, request,
				JSONObject.class);*/
		ResponseEntity<JSONObject> statusobj = restTemplate.exchange(builder
				.build().toUri(), HttpMethod.GET, request, JSONObject.class);

		return statusobj.getBody();
	}

}
