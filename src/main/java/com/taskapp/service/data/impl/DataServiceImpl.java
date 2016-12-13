package com.taskapp.service.data.impl;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrDocumentList;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import redis.clients.jedis.Jedis;

import com.google.gson.Gson;
import com.taskapp.dbOperation.DbOperationService;
import com.taskapp.model.UserModel;
import com.taskapp.service.data.DataService;
import com.taskapp.solr.SearchHandler;
import com.taskapp.utils.Encrypt;
import com.taskapp.utils.UUIDGeneratorForUser;

@Service
public class DataServiceImpl implements DataService {

	@Autowired
	public DbOperationService dbservice;

	@Autowired
	public Encrypt encryptor;
	
	@Autowired
	private UUIDGeneratorForUser generateuuid;

	@Autowired
	private RedisTemplate<String, Object> template;
	
	@Autowired
	private SearchHandler solrService;

	@Override
	public HttpStatus saveUser(UserModel usermodel) throws NoSuchAlgorithmException, SolrServerException, IOException {
		String encryptUserPassword = encryptor.textEncrypt(usermodel
				.getPassword());
		usermodel.setPassword(encryptUserPassword);
		dbservice.saveUser(usermodel);
		solrService.indexuser(usermodel);
		return HttpStatus.OK;
	}

	@Override
	public JSONObject authenticate(JSONObject userobj) throws ParseException {
		
		HttpStatus httpstatus = null;
		UserModel user = dbservice.getUserObj((String)userobj.get("email"));
		
		boolean authStatus = encryptor.compareWithEncryptText((String)userobj.get("password"), user.getPassword());
		JSONObject authResponse = new JSONObject();
		
		
		if(authStatus){
			UUID uuidForUser = generateuuid.generateUUID();
			System.out.println("uuid :: "+uuidForUser);
			
			Gson gson = new Gson();
		    String json = gson.toJson(user);    
		    JSONParser parser = new JSONParser();
		    JSONObject jsonobj = (JSONObject) parser.parse(json);
		    jsonobj.remove("password");

		    System.out.println(jsonobj);
		    
		    httpstatus = setUserInRedis(uuidForUser, jsonobj);
		    authResponse.put("key", "user:"+uuidForUser);
		    authResponse.put("status", HttpStatus.OK.value());
		}else{
			authResponse.put("status", HttpStatus.BAD_GATEWAY.value());
		}
		
		return authResponse;
	}

	@Override
	public JSONObject getUser(final String uuid) {

		final Jedis jedis = new Jedis("localhost");
		JSONObject redisobj = new JSONObject();

		Map<Object, Object> userobj = new HashMap<Object, Object>();
		userobj = template.opsForHash().entries(uuid);
		redisobj.put(uuid, userobj);

		return redisobj;
	}

	private HttpStatus setUserInRedis(UUID uuid,final JSONObject usermodel) {
		final String key = String.format("user:%s", uuid);

		template.opsForHash().putAll(key, usermodel);
		//template.expire(key, 2, TimeUnit.MINUTES);
		return HttpStatus.OK;
	}

	@Override
	public SolrDocumentList fetchTag(String searchVal) throws SolrServerException, IOException {
		return solrService.fetchTag(searchVal);
	}

	@Override
	public void deleteTag(String fieldName, String fieldValue)
			throws SolrServerException, IOException {
		solrService.deleteTag(fieldName, fieldValue);
		
	}

	@Override
	public void createTag(String tagName, String tagType, String tagValue)
			throws SolrServerException, IOException {
		solrService.createTag(tagName, tagType, tagValue);
		
	}

}
