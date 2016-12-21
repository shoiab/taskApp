package com.taskapp.service.data.impl;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.solr.client.solrj.SolrServerException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import redis.clients.jedis.Jedis;

import com.google.gson.Gson;
import com.taskapp.constants.Constants;
import com.taskapp.dbOperation.DbOperationService;
import com.taskapp.model.UserModel;
import com.taskapp.service.data.DataService;
import com.taskapp.service.data.TagService;
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
	
	@Autowired
	private TagService tagservice;

	@Override
	public HttpStatus saveUser(UserModel usermodel) throws NoSuchAlgorithmException, SolrServerException, IOException {
		String encryptUserPassword = encryptor.textEncrypt(usermodel
				.getPassword());
		usermodel.setPassword(encryptUserPassword);
		JSONObject userobj = dbservice.saveUser(usermodel);
		if(userobj.get("HTTPStatus") != HttpStatus.FOUND){
			//solrService.indexuser(usermodel);
			dbservice.createTag(usermodel.getName(), Constants.TAG_TYPE_USER, usermodel.getEmail());
			tagservice.createTag(usermodel.getName(),Constants.TAG_TYPE_USER,usermodel.getEmail(), userobj.get("id").toString());
		}
		return (HttpStatus) userobj.get("HTTPStatus");
	}

	@Override
	public JSONObject authenticate(JSONObject userobj) throws ParseException {
		
		HttpStatus httpstatus = null;
		UserModel user = dbservice.getUserObj(userobj.get("email").toString());
		
		boolean authStatus = encryptor.compareWithEncryptText(userobj.get("password").toString(), user.getPassword());
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

		final Jedis jedis = new Jedis();
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
	public JSONObject changePassword(UserModel usermodel, String auth_key) {
		String encryptUserPassword = encryptor.textEncrypt(usermodel.getPassword());
		dbservice.updateUserPassword(encryptUserPassword, usermodel.getEmail());
		template.delete(auth_key);
		JSONObject statusobj = new JSONObject();
		statusobj.put("status", HttpStatus.OK.value());
		statusobj.put("message", "Password successfully updated");
		return statusobj;
	}

}
