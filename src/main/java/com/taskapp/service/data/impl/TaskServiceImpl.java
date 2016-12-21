package com.taskapp.service.data.impl;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.taskapp.dbOperation.DbOperationService;
import com.taskapp.model.TaskModel;
import com.taskapp.service.data.TaskService;
import com.taskapp.solr.SearchHandler;

@Service
public class TaskServiceImpl implements TaskService{
	
	@Autowired
	SearchHandler solrservice;
	
	@Autowired
	DbOperationService dbservice;

	@Override
	public HttpStatus createTask(TaskModel taskModel) {
		
		JSONObject taskobj = dbservice.createTask(taskModel);
		return (HttpStatus) taskobj.get("httpStatus");
	}

	@Override
	public TaskModel fetchTask(String taskName) {
		return dbservice.fetchTask(taskName);
	}

}
