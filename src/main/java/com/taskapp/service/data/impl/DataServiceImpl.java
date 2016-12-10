package com.taskapp.service.data.impl;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.taskapp.dbOperation.DbOperationService;
import com.taskapp.model.UserModel;
import com.taskapp.service.data.DataService;
import com.taskapp.utils.Encrypt;

@Service
public class DataServiceImpl implements DataService {

	@Autowired
	public DbOperationService dbservice;

	@Autowired
	public Encrypt encryptor;

	@Autowired
	private RedisTemplate<String, Object> template;

	@Override
	public void saveUser(UserModel usermodel) throws NoSuchAlgorithmException {
		String encryptUserPassword = encryptor.textEncrypt(usermodel
				.getPassword());
		usermodel.setPassword(encryptUserPassword);
		dbservice.saveUser(usermodel);
	}

	@Override
	public boolean authenticate(JSONObject auth) {
		UserModel user = dbservice.getUserObj(auth.get("email").toString());
		return encryptor.compareWithEncryptText(
				auth.get("password").toString(), user.getPassword());

	}

	@Override
	public UserModel getUser(final String key) {

		final String name = (String) template.opsForHash().get(key, "name");
		final String email = (String) template.opsForHash().get(key, "email");

		return new UserModel(name, email);
	}

	@Override
	public void setUser(final UserModel usermodel) {
		final String key = String.format("user:%s", usermodel.getName());
		final Map<String, Object> properties = new HashMap<String, Object>();

		properties.put("name", usermodel.getName());
		properties.put("email", usermodel.getEmail());

		template.opsForHash().putAll(key, properties);
	}

}
