package com.taskapp.service.data.impl;

import java.io.IOException;

import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taskapp.service.data.TagService;
import com.taskapp.solr.SearchHandler;

@Service
public class TagServiceImpl implements TagService{
	
	@Autowired
	private SearchHandler solrService;
	
	
	@Override
	public SolrDocumentList fetchTag(String searchVal, String searchField) throws SolrServerException, IOException {
		return solrService.fetchTag(searchVal, searchField);
	}

	/*@Override
	public void deleteTag(String fieldName, String fieldValue)
			throws SolrServerException, IOException {
		if(fieldName.equalsIgnoreCase("tagName")){
			solrService.deleteTag(fieldName, fieldValue);
		}
	}*/

	@Override
	public void createTag(String tagName, String tagType, String tagValue)
			throws SolrServerException, IOException {
		solrService.createTag(tagName, tagType, tagValue);
		
	}

}
