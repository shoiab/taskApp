package com.taskapp.service.data;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrDocumentList;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.springframework.http.HttpStatus;

import com.taskapp.model.UserModel;

public interface DataService {
	public HttpStatus saveUser(UserModel usermodel) throws NoSuchAlgorithmException, SolrServerException, IOException;

	public JSONObject authenticate(JSONObject userobj) throws ParseException;
	
	public JSONObject getUser( final String key );

	public SolrDocumentList fetchTag(String searchVal) throws SolrServerException, IOException;

	public void deleteTag(String fieldName, String fieldValue) throws SolrServerException, IOException;

	public void createTag(String tagName, String tagType, String tagValue) throws SolrServerException, IOException;
	
}
