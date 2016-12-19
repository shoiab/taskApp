package com.taskapp.dbOperation.impl;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.util.JSON;
import com.taskapp.dbOperation.DbOperationService;
import com.taskapp.model.TagModel;
import com.taskapp.model.UserModel;

@Service
public class DbOperationServiceImpl implements DbOperationService {

	@Autowired
	private Environment environment;

	@Autowired
	private MongoClient mongoClient;

	@Override
	public HttpStatus saveUser(UserModel usermodel) {
		MongoDatabase db = mongoClient.getDatabase(environment
				.getProperty("mongo.dataBase"));

		MongoCollection<BasicDBObject> coll = db.getCollection(
				environment.getProperty("mongo.userCollection"),
				BasicDBObject.class);

		Gson gson = new Gson();
		
		BasicDBObject whereQuery = new BasicDBObject();
		whereQuery.put("email", usermodel.getEmail());
		FindIterable<BasicDBObject> obj = coll.find(whereQuery);
		
		if (obj.first() == null) {
			BasicDBObject basicobj = (BasicDBObject) JSON.parse(gson
					.toJson(usermodel));
			
			coll.insertOne(basicobj);
			return HttpStatus.OK;
		}else{
			return HttpStatus.FOUND;
		}

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

	@Override
	public void createTag(String name, String tagTypeUser, String email) {
		MongoDatabase db = mongoClient.getDatabase(environment
				.getProperty("mongo.dataBase"));

		MongoCollection<BasicDBObject> coll = db.getCollection(
				environment.getProperty("mongo.tagCollection"),
				BasicDBObject.class);
		
		TagModel tagmodel = new TagModel();
		tagmodel.setTagName(name);
		tagmodel.setTagType(tagTypeUser);
		tagmodel.setTagValue(email);
		Gson gson = new Gson();
		BasicDBObject basicobj = (BasicDBObject) JSON.parse(gson
				.toJson(tagmodel));
		
		coll.insertOne(basicobj);
		
	}

	@Override
	public void updateUserPassword(String encryptUserPassword, String email) {
		MongoDatabase db = mongoClient.getDatabase(environment
				.getProperty("mongo.dataBase"));

		MongoCollection<BasicDBObject> coll = db.getCollection(
				environment.getProperty("mongo.userCollection"),
				BasicDBObject.class);

		//Gson gson = new Gson();
		
		BasicDBObject whereQuery = new BasicDBObject();
		whereQuery.put("email", email);
		FindIterable<BasicDBObject> obj = coll.find(whereQuery);
		
		if(obj.first() != null){
			Document newDocument = new Document();
			Document searchQuery = new Document().append("email",email);
			newDocument.put("$set", new BasicDBObject("password", encryptUserPassword));
			coll.updateOne(searchQuery, newDocument);
		}
		
		
	}

}
