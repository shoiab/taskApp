package com.taskapp.controller;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.taskapp.service.data.TagService;

@RestController
@RequestMapping("/tags")
public class TagController {

	@Autowired
	TagService tagservice;

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

	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody SolrDocumentList getindex(
			@RequestHeader(value = "auth_key") String auth_key,
			@RequestParam(value = "search") String searchVal,
			@RequestParam(value = "field") String searchField)
			throws NoSuchAlgorithmException, SolrServerException, IOException {

		return tagservice.fetchTag(searchVal, searchField);

	}
	
	@RequestMapping(value= "/getAllUsers", method = RequestMethod.GET)
	public @ResponseBody SolrDocumentList getAllUsers(
			@RequestHeader(value = "auth_key") String auth_key)
			throws NoSuchAlgorithmException, SolrServerException, IOException {

		return tagservice.getAllUsers();

	}
	
	@RequestMapping(value= "/getAllGroups", method = RequestMethod.GET)
	public @ResponseBody SolrDocumentList getAllGroups(
			@RequestHeader(value = "auth_key") String auth_key)
			throws NoSuchAlgorithmException, SolrServerException, IOException {

		return tagservice.getAllGroups();

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
