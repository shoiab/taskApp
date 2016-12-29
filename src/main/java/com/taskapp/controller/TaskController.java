package com.taskapp.controller;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

import javax.inject.Inject;

import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrDocumentList;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
import com.taskapp.service.data.DataService;
import com.taskapp.service.data.TaskService;

@RestController
public class TaskController {

	@Inject
	private RestTemplate restTemplate;

	@Autowired
	TaskService taskservice;
	
	@Autowired
	DataService dataservice;

	@RequestMapping(value = "/createTask", method = RequestMethod.POST)
	public @ResponseBody JSONObject createTask(
			@RequestHeader(value = "auth_key") String auth_key,
			@RequestBody TaskModel taskModel) throws NoSuchAlgorithmException,
			SolrServerException, IOException {
		taskModel.setTaskCreationDate(new Date());
		taskModel.setTaskCreator(dataservice.getUserEmail(auth_key));
		HttpStatus status = taskservice.createTask(taskModel);

		JSONObject statusobj = new JSONObject();
		statusobj.put("status", status.value());
		if (status == HttpStatus.FOUND) {
			statusobj.put("message", "Task already exists");
		} else {
			statusobj.put("message", "Task created successfully");
		}

		return statusobj;
	}

	/*@RequestMapping(value = "/pingAnotherMicroservice", method = RequestMethod.GET)
	public @ResponseBody String pingMS() throws URISyntaxException {

		URI url = new URI("http://localhost:8081/api/taskapp/sendEmail");

		String str = restTemplate.getForObject(url, String.class);
		return str;
	}*/

	@RequestMapping(value = "/postTask", method = RequestMethod.GET)
	public @ResponseBody JSONObject notifyTask(
			@RequestHeader(value = "auth_key") String auth_key,
			@RequestParam(value = "taskTitle") String taskName)
			throws URISyntaxException {

		TaskModel taskmodel = taskservice.fetchTask(taskName);
		JSONObject statusobj = new JSONObject();

		if (taskmodel != null) {

			Gson gson = new Gson();
			String taskjson = gson.toJson(taskmodel);

			URI url = new URI("http://localhost:8081/api/notifier/sendEmail");

			statusobj = restTemplate.postForObject(url, taskjson,
					JSONObject.class);
		}

		return statusobj;
	}
	
	@RequestMapping(value = "/getUserTasks", method = RequestMethod.POST)
	public @ResponseBody SolrDocumentList getMyTasks(
			@RequestHeader(value = "auth_key") String auth_key)
			throws NoSuchAlgorithmException, SolrServerException, IOException {
		return taskservice.getAllTasks(auth_key);
	}

}
