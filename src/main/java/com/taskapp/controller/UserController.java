package com.taskapp.controller;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrRequest.METHOD;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.taskapp.model.UserModel;
import com.taskapp.service.data.DataService;

@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	DataService dataservice;
	
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public @ResponseBody HttpStatus createUser(@RequestBody UserModel usermodel) throws NoSuchAlgorithmException {
		dataservice.saveUser(usermodel);
		return HttpStatus.OK;
	}
	
	@RequestMapping(value = "/auth", method = RequestMethod.POST)
	public @ResponseBody JSONObject authenticate(@RequestParam (value = "email") String email, @RequestParam (value = "password") String password) throws NoSuchAlgorithmException, ParseException {
		JSONObject userobj = new JSONObject();
		userobj.put("email", email);
		userobj.put("password", password);
		return dataservice.authenticate(userobj);
	}
	
	@RequestMapping(value = "/getuserfromcache", method = RequestMethod.POST)
	public @ResponseBody JSONObject getUserFromCache(@RequestHeader (value = "key") String auth_key) throws NoSuchAlgorithmException {
		return dataservice.getUser(auth_key);
	}
	
	@RequestMapping(value = "/index", method = RequestMethod.POST)
	public void indextoSolr() throws NoSuchAlgorithmException, SolrServerException, IOException {
		Map<String, String> userMap = new HashMap<String, String>();
		for(int i = 0 ; i <10 ; i++){
			userMap.put("user"+i, "hello"+i);
		}
		//HttpSolrServer server = new HttpSolrServer("http://HOST:8983/solr/");
		String solrUrl = "http://localhost:8983/solr/taskapp";
		
		Set<String> keys = userMap.keySet();

		HttpSolrClient server = new HttpSolrClient(solrUrl);
		SolrInputDocument doc = new SolrInputDocument();
		SolrInputDocument doc1 = new SolrInputDocument();
		SolrInputDocument doc2 = new SolrInputDocument();
		SolrInputDocument doc3 = new SolrInputDocument();
		SolrInputDocument doc4 = new SolrInputDocument();
		SolrInputDocument doc5 = new SolrInputDocument();
		
		/*for (String key : keys) {
			
			doc.addField("name", key);
			doc.addField("detail", userMap.get(key));
		}*/
		
		doc.addField("name", "gayatri");
		doc1.addField("name", "shoiab");
		doc2.addField("name", "zee");
		doc3.addField("name", "atif");
		doc4.addField("name", "shobana");
		doc5.addField("name", "zoya");
		
		doc.addField("detail", "hello i am gayatri");
		doc1.addField("detail", "good evening");
		doc2.addField("detail", "nothing is wrong");
		doc3.addField("detail", "finest is the finest");
		
		server.add(doc);
		server.add(doc1);
		server.add(doc2);
		server.add(doc3);
		server.add(doc4);
		server.add(doc5);
		server.commit();
		server.close();
		
	}
	
	@RequestMapping(value = "/getIndex", method = RequestMethod.POST)
	public @ResponseBody SolrDocumentList getindex(@RequestParam (value = "search") String searchVal) throws NoSuchAlgorithmException, SolrServerException, IOException {
		
		String solrUrl = "http://localhost:8983/solr/taskapp";

		HttpSolrClient server = new HttpSolrClient(solrUrl);
		
		SolrDocumentList docsans = new SolrDocumentList();
		SolrQuery solrQuery = new SolrQuery();
		solrQuery.setQuery(searchVal + "*");
		solrQuery.setFields("name");
		QueryResponse rsp = server.query(solrQuery, METHOD.POST);
		docsans = rsp.getResults();
		System.out.println(docsans);
		
		server.close();
		return docsans;
	}
	
	@RequestMapping(value = "/deleteIndex", method = RequestMethod.POST)
	public void deleteindex(@RequestParam (value = "name") String name) throws NoSuchAlgorithmException, SolrServerException, IOException {
		String solrUrl = "http://localhost:8983/solr/taskapp";

		HttpSolrClient server = new HttpSolrClient(solrUrl);
		server.deleteByQuery("name:"+name);
		server.commit();
		server.close();
	}
	
}
