package com.taskapp.service.data.impl;

import java.io.IOException;

import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.taskapp.constants.Constants;
import com.taskapp.dbOperation.DbOperationService;
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
		solrService.createTag(groupName, Constants.TAG_TYPE_GROUP, emails);
		dbservice.createTag(groupName, Constants.TAG_TYPE_GROUP, emails);
		return HttpStatus.OK;
	}

}
