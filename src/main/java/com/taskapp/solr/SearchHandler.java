package com.taskapp.solr;

import java.io.IOException;

import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrDocumentList;

import com.taskapp.model.UserModel;

public interface SearchHandler {

	void indexuser(UserModel usermodel) throws SolrServerException, IOException;

	SolrDocumentList fetchTag(String searchVal) throws SolrServerException, IOException;

	void deleteTag(String fieldName, String fieldValue) throws SolrServerException, IOException;

	void createTag(String tagName, String tagType, String tagValue) throws SolrServerException, IOException;

}
