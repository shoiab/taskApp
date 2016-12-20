package com.taskapp.service.data.impl;

import java.io.IOException;
import java.util.List;

import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.taskapp.constants.Constants;
import com.taskapp.dbOperation.DbOperationService;
import com.taskapp.model.UserModel;
import com.taskapp.service.data.CSVUserDataService;
import com.taskapp.solr.SearchHandler;
import com.taskapp.utils.Encrypt;

@Service
public class CSVUserDataServiceImpl implements CSVUserDataService{
	
	@Autowired
	DbOperationService dbservice;
	
	@Autowired
	private SearchHandler solrService;
	
	@Autowired
	private Encrypt encryptor;
	
	@Override
	public HttpStatus saveUser(List<UserModel> userlist) throws SolrServerException, IOException {
		for(UserModel usermodel : userlist){
			String encryptedPassword = encryptor.textEncrypt(usermodel.getPassword());
			usermodel.setPassword(encryptedPassword); 
			HttpStatus status = dbservice.saveUser(usermodel);
			if(status != HttpStatus.FOUND){
				solrService.createTag(usermodel.getName(), Constants.TAG_TYPE_USER, usermodel.getEmail());
				dbservice.createTag(usermodel.getName(), Constants.TAG_TYPE_USER, usermodel.getEmail());
			}			
		}
		return HttpStatus.OK;
	}
	
	
}
