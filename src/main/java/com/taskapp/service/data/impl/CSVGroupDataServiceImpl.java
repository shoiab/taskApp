package com.taskapp.service.data.impl;

import java.io.IOException;
import java.util.Date;

import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.taskapp.constants.Constants;
import com.taskapp.dbOperation.DbOperationService;
import com.taskapp.model.GroupModel;
import com.taskapp.service.data.CSVGroupDataService;
import com.taskapp.solr.SearchHandler;

@Service
public class CSVGroupDataServiceImpl implements CSVGroupDataService{
	
	@Autowired
	DbOperationService dbservice;
	
	@Autowired
	private SearchHandler solrService;

	@Override
	public HttpStatus createGroup(String emails, String groupName) throws SolrServerException, IOException {
		GroupModel groupmodel = new GroupModel();
		groupmodel.setDateOfCreation(new Date());
		groupmodel.setGroupName(groupName);
		groupmodel.setGroupMailList(emails);
		HttpStatus status = dbservice.createGroup(groupmodel);
		if(status == HttpStatus.OK){
			solrService.createTag(groupName, Constants.TAG_TYPE_GROUP, emails);
			dbservice.createTag(groupName, Constants.TAG_TYPE_GROUP, emails);
		}
		return status;
	}

}
