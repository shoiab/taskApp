package com.taskapp.solr.impl;

import java.io.IOException;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.SolrRequest.METHOD;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.taskapp.constants.Constants;
import com.taskapp.model.UserModel;
import com.taskapp.solr.SearchHandler;

@Service
public class SearchManagerImpl implements SearchHandler {

	@Autowired
	Environment env;

	@Override
	public void indexuser(UserModel usermodel) throws SolrServerException,
			IOException {
		String solrUrl = env.getProperty(Constants.SOLR_URL);

		HttpSolrClient server = new HttpSolrClient(solrUrl);
		SolrInputDocument userdoc = new SolrInputDocument();

		userdoc.addField("tagName", usermodel.getName());
		userdoc.addField("tagValue", usermodel.getEmail());
		userdoc.addField("tagType", Constants.TAG_TYPE_USER);

		server.add(userdoc);
		server.commit();
		server.close();
	}

	@Override
	public SolrDocumentList fetchTag(String searchVal)
			throws SolrServerException, IOException {
		String solrUrl = env.getProperty(Constants.SOLR_URL);

		HttpSolrClient server = new HttpSolrClient(solrUrl);

		SolrDocumentList docsans = new SolrDocumentList();
		SolrQuery solrQuery = new SolrQuery();
		solrQuery.setQuery(searchVal + "*");
		solrQuery.setFields("tagName", "tagValue");
		QueryResponse rsp = server.query(solrQuery, METHOD.POST);
		docsans = rsp.getResults();
		System.out.println(docsans);

		server.close();
		return docsans;
	}

	@Override
	public void deleteTag(String fieldName, String fieldValue)
			throws SolrServerException, IOException {

		String solrUrl = env.getProperty(Constants.SOLR_URL);
		HttpSolrClient server = new HttpSolrClient(solrUrl);
		server.deleteByQuery(fieldName + ":" + fieldValue);
		server.commit();
		server.close();

	}

	@Override
	public void createTag(String tagName, String tagType, String tagValue)
			throws SolrServerException, IOException {
		String solrUrl = env.getProperty(Constants.SOLR_URL);

		HttpSolrClient server = new HttpSolrClient(solrUrl);
		SolrInputDocument tagdoc = new SolrInputDocument();

		tagdoc.addField("tagName", tagName);
		tagdoc.addField("tagType", tagType);
		tagdoc.addField("tagValue", tagValue);

		server.add(tagdoc);
		server.commit();
		server.close();

	}

}
