package com.taskapp.controller;

import java.io.IOException;

import javax.inject.Inject;

import org.apache.solr.client.solrj.SolrServerException;
import org.json.simple.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
public class CSVGroupController {
	
	@Inject
	private RestTemplate restTemplate;

	@RequestMapping(value = "/createGroupFromCSV", method = RequestMethod.POST)
	public JSONObject createGroupFromCSV(
			@RequestHeader(value = "auth_key") String auth_key,
			@RequestParam(value = "groupName") String groupName)
			throws SolrServerException, IOException {
		
		String url = "http://localhost:8087/api/task/createGroupFromCSV";
		
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
		headers.add("auth_key", auth_key);
		
		restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

		HttpEntity<String> request = new HttpEntity<String>(headers);
		
		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url)
		        // Add query parameter
		        .queryParam("groupName", groupName);
		     
		JSONObject statusobj = new JSONObject();
		
		statusobj = restTemplate
				.postForObject(builder.build().toUri(), request, JSONObject.class);
		
		return statusobj;

	}

}
