package com.taskapp.dbOperation.impl;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.util.JSON;
import com.taskapp.dbOperation.DbOperationService;
import com.taskapp.model.UserModel;

@Service
public class DbOperationServiceImpl implements DbOperationService {

	@Autowired
	private Environment environment;

	@Autowired
	private MongoClient mongoClient;

	@Override
	public void saveUser(UserModel usermodel) {
		MongoDatabase db = mongoClient.getDatabase(environment
				.getProperty("mongo.dataBase"));

		MongoCollection<BasicDBObject> coll = db.getCollection(
				environment.getProperty("mongo.userCollection"),
				BasicDBObject.class);

		Gson gson = new Gson();

		BasicDBObject basicobj = (BasicDBObject) JSON.parse(gson
				.toJson(usermodel));
		coll.insertOne(basicobj);

	}

	@Override
	public UserModel getUserObj(String email) {
		MongoDatabase db = mongoClient.getDatabase(environment
				.getProperty("mongo.dataBase"));

		MongoCollection<BasicDBObject> coll = db.getCollection(
				environment.getProperty("mongo.userCollection"),
				BasicDBObject.class);
		BasicDBObject whereQuery = new BasicDBObject();
		whereQuery.put("email", email);

		FindIterable<BasicDBObject> obj = coll.find(whereQuery);
		UserModel userModel = new UserModel();
		if (obj.first() != null) {
			userModel = (UserModel) (new Gson()).fromJson(obj.first().toString(),
					UserModel.class);
		}
		return userModel;
	}

}