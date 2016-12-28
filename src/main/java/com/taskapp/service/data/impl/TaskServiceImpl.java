package com.taskapp.service.data.impl;

import java.io.IOException;

import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrDocumentList;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.taskapp.constants.Constants;
import com.taskapp.dbOperation.DbOperationService;
import com.taskapp.model.TaskModel;
import com.taskapp.service.data.DataService;
import com.taskapp.service.data.TagService;
import com.taskapp.service.data.TaskService;
import com.taskapp.solr.SearchHandler;

@Service
public class TaskServiceImpl implements TaskService{
	
	@Autowired
	SearchHandler solrservice;
	
	@Autowired
	DbOperationService dbservice;
	
	@Autowired
	DataService dataservice;

	@Override
	public HttpStatus createTask(TaskModel taskModel) throws SolrServerException, IOException {
		
		JSONObject taskobj = dbservice.createTask(taskModel);
		taskModel.setTaskid(taskobj.get("id").toString());
		if(taskobj.get("httpStatus") != HttpStatus.FOUND){
			solrservice.createTask(taskModel);
			dataservice.createTaskTag(taskModel);
		}
		return (HttpStatus) taskobj.get("httpStatus");
	}

	@Override
	public TaskModel fetchTask(String taskName) {
		return dbservice.fetchTask(taskName);
	}

	@Override
	public SolrDocumentList getAllTasks(String auth_key) throws SolrServerException, IOException {
		return solrservice.getAllTasks(dataservice.getUserEmail(auth_key));
	}

}
