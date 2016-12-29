package com.taskapp.controller;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.inject.Inject;

import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.taskapp.service.data.CSVUserDataService;
import com.taskapp.utils.CSVUserhelper;

@RestController
public class CSVUserController {

	@Autowired
	private CSVUserhelper csvhelper;

	@Autowired
	CSVUserDataService csvdataservice;
	
	@Inject
	private RestTemplate restTemplate;

	@RequestMapping(value = "/uploadUsersFromCSV", method = RequestMethod.POST)
	public HttpStatus uploadUsersFromCSV(
			@RequestHeader(value = "auth_key") String auth_key)
			throws SolrServerException, IOException, URISyntaxException {
		
		URI url = new URI("http://localhost:8083/api/taskapp/uploadUsersFromCSV");
		
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
		headers.add("auth_key", auth_key);
		
		HttpEntity<String> request = new HttpEntity<String>(headers);
		
		HttpStatus status = restTemplate
				.postForObject(url, request, HttpStatus.class);
		
		return status;
	}

}
