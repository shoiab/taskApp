package com.taskapp.controller;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.NoSuchAlgorithmException;

import org.apache.log4j.Logger;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
public class TagController {
	
	static Logger logger = Logger.getLogger(TagController.class.getName());

	@Autowired
	RestTemplate restTemplate;
  
	/*
	 * @RequestMapping(method = RequestMethod.POST) public void
	 * addTag(@RequestHeader(value = "auth_key") String auth_key,
	 * 
	 * @RequestParam(value = "tagName") String tagName,
	 * 
	 * @RequestParam(value = "tagType") String tagType,
	 * 
	 * @RequestParam(value = "tagValue") String tagValue) throws
	 * NoSuchAlgorithmException, SolrServerException, IOException {
	 * tagservice.createTag(tagName, tagType, tagValue);
	 * 
	 * }
	 */

	@RequestMapping(value= "/fetchTag", method = RequestMethod.GET)
	public @ResponseBody SolrDocumentList fetchTag(
			@RequestHeader(value = "auth_key") String auth_key,
			@RequestParam(value = "search") String searchVal,
			@RequestParam(value = "field") String searchField)
			throws NoSuchAlgorithmException, SolrServerException, IOException, URISyntaxException {
	
		/*String url = "http://localhost:8087/api/task/fetchTag";
		
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", "application/json");
		headers.set("auth_key", auth_key);
		
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
		        // Add query parameter
		        .queryParam("search", searchVal)
		        .queryParam("field", searchField);
		HttpEntity<String> request = new HttpEntity<String>(headers);
		restTemplate.getMessageConverters().add(
				new MappingJackson2HttpMessageConverter());
		

		HttpEntity<SolrDocumentList> usersObj = restTemplate.exchange(builder.build().encode().toUri(),
				HttpMethod.GET, request, SolrDocumentList.class);
		return usersObj.getBody();	*/
		
		
		String url = "http://localhost:8087/api/task/fetchTag";
		//URI url = new URI("http://localhost:8084/api/task/fetchTag");
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
		headers.add("auth_key", auth_key);
		
		restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
		
		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url)
		        // Add query parameter
		        .queryParam("search", searchVal)
		        .queryParam("field", searchField);
		HttpEntity<String> request = new HttpEntity<String>(headers);
		//HttpEntity<String> request = new HttpEntity<String>(headers);
		//SolrDocumentList tagobj = restTemplate.getForObject(builder.build().toUri().toString(), SolrDocumentList.class, request);
		HttpEntity<SolrDocumentList> tagobj = restTemplate.exchange(builder.build().toUri(),
				HttpMethod.GET, request, SolrDocumentList.class);
		
		
		logger.info(tagobj.getBody());
		return tagobj.getBody();		
	}
	
	@RequestMapping(value= "/getAllUsers", method = RequestMethod.GET)
	public @ResponseBody SolrDocumentList getAllUsers(
			@RequestHeader(value = "auth_key") String auth_key)
			throws NoSuchAlgorithmException, SolrServerException, IOException {
		
		String url = "http://localhost:8087/api/task/getAllUsers";
		
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
		headers.add("auth_key", auth_key);
		
		//restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

		HttpEntity<String> request = new HttpEntity<String>(headers);
		
		HttpEntity<SolrDocumentList> statusobj = restTemplate
				.exchange(url, HttpMethod.GET, request, SolrDocumentList.class);
		
		return statusobj.getBody();	
	}
	
	@RequestMapping(value= "/getAllGroups", method = RequestMethod.GET)
	public @ResponseBody SolrDocumentList getAllGroups(
			@RequestHeader(value = "auth_key") String auth_key)
			throws NoSuchAlgorithmException, SolrServerException, IOException {
		
		String url = "http://localhost:8087/api/task/getAllGroups";
		
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
		headers.add("auth_key", auth_key);
		
		//restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

		HttpEntity<String> request = new HttpEntity<String>(headers);
		
		HttpEntity<SolrDocumentList> statusobj = restTemplate
				.exchange(url, HttpMethod.GET, request, SolrDocumentList.class);
		
		return statusobj.getBody();	
	}

	/*
	 * @RequestMapping(method = RequestMethod.DELETE) public void
	 * deleteindex(@RequestHeader(value = "auth_key") String auth_key,
	 * 
	 * @RequestParam(value = "fieldName") String fieldName,
	 * 
	 * @RequestParam(value = "fieldValue") String fieldValue) throws
	 * NoSuchAlgorithmException, SolrServerException, IOException {
	 * tagservice.deleteTag(fieldName, fieldValue); }
	 */

}
