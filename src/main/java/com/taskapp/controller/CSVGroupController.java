package com.taskapp.controller;

import java.io.IOException;

import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.taskapp.service.data.CSVGroupDataService;
import com.taskapp.utils.CSVGroupHelper;

@RestController
public class CSVGroupController {

	@Autowired
	private CSVGroupHelper csvgrouphelper;

	@Autowired
	CSVGroupDataService csvgroupservice;

	@RequestMapping(value = "/createGroupFromCSV", method = RequestMethod.POST)
	public HttpStatus createGroupFromCSV(
			@RequestHeader(value = "auth_key") String auth_key,
			@RequestParam(value = "groupName") String groupName)
			throws SolrServerException, IOException {
		String emails = csvgrouphelper.convertCSVToGroupList();
		HttpStatus status = csvgroupservice.createGroup(emails, groupName);
		return status;

	}

}
