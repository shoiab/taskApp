package com.taskapp.controller;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

import javax.inject.Inject;

import org.apache.solr.client.solrj.SolrServerException;
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
import com.taskapp.service.data.TaskService;

@RestController
public class TaskController {

	@Inject
	private RestTemplate restTemplate;

	@Autowired
	TaskService taskservice;

	@RequestMapping(value = "/createTask", method = RequestMethod.POST)
	public @ResponseBody JSONObject createTask(
			@RequestHeader(value = "auth_key") String auth_key,
			@RequestBody TaskModel taskModel) throws NoSuchAlgorithmException,
			SolrServerException, IOException {
		taskModel.setTaskCreationDate(new Date());
		HttpStatus status = taskservice.createTask(taskModel);

		JSONObject statusobj = new JSONObject();
		statusobj.put("status", status.value());
		if (status == HttpStatus.FOUND) {
			statusobj.put("message", "Task already exists");
		} else {
			statusobj.put("message", "Task registered successfully");
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
			String json = gson.toJson(taskmodel);

			URI url = new URI("http://localhost:8081/api/taskapp/sendEmail");

			statusobj = restTemplate.postForObject(url, json,
					JSONObject.class);
		}

		return statusobj;
	}

}
