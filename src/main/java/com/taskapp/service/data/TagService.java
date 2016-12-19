package com.taskapp.service.data;

import java.io.IOException;

import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrDocumentList;

public interface TagService {

	SolrDocumentList fetchTag(String searchVal, String searchField) throws SolrServerException, IOException;

	/*public void deleteTag(String fieldName, String fieldValue) throws SolrServerException, IOException;*/

	public void createTag(String tagName, String tagType, String tagValue) throws SolrServerException, IOException;

}
