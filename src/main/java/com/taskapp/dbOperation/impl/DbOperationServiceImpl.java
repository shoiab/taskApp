package com.taskapp.dbOperation.impl;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.json.simple.JSONObject;
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
import com.taskapp.model.GroupModel;
import com.taskapp.model.TagModel;
import com.taskapp.model.TaskModel;
import com.taskapp.model.UserModel;

@Service
public class DbOperationServiceImpl implements DbOperationService {

	@Autowired
	private Environment environment;

	@Autowired
	private MongoClient mongoClient;

	@Override
	public JSONObject saveUser(UserModel usermodel) {
		MongoDatabase db = mongoClient.getDatabase(environment
				.getProperty("mongo.dataBase"));

		MongoCollection<BasicDBObject> coll = db.getCollection(
				environment.getProperty("mongo.userCollection"),
				BasicDBObject.class);

		Gson gson = new Gson();
		JSONObject json = new JSONObject();
		
		BasicDBObject whereQuery = new BasicDBObject();
		whereQuery.put("email", usermodel.getEmail());
		FindIterable<BasicDBObject> obj = coll.find(whereQuery);
		
		BasicDBObject basicUserObj = (BasicDBObject) JSON.parse(gson
				.toJson(usermodel));
		if (obj.first() == null) {
			
			coll.insertOne(basicUserObj);
			ObjectId id = basicUserObj.getObjectId("_id");
			json.put("HTTPStatus", HttpStatus.OK);
			json.put("id",id);
			return json;
			
		}else{
			
			ObjectId id = basicUserObj.getObjectId("_id");
			json.put("HTTPStatus", HttpStatus.FOUND);
			json.put("id",id);
			return json;
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

	@Override
	public JSONObject createGroup(GroupModel groupmodel) {
		MongoDatabase db = mongoClient.getDatabase(environment
				.getProperty("mongo.dataBase"));

		MongoCollection<BasicDBObject> coll = db.getCollection(
				environment.getProperty("mongo.groupCollection"),
				BasicDBObject.class);

		Gson gson = new Gson();
		
		JSONObject json = new JSONObject();
		
		BasicDBObject whereQuery = new BasicDBObject();
		whereQuery.put("groupName", groupmodel.getGroupName());
		FindIterable<BasicDBObject> obj = coll.find(whereQuery);
		
		BasicDBObject basicGroupObj = (BasicDBObject) JSON.parse(gson
				.toJson(groupmodel));
		
		if (obj.first() == null) {
			
			coll.insertOne(basicGroupObj);
			json.put("HTTPStatus", HttpStatus.OK);
			json.put("id", basicGroupObj.get("_id"));
			return json;
		}else{
			json.put("HTTPStatus", HttpStatus.FOUND);
			json.put("id", basicGroupObj.get("_id"));
			return json;
		}
	}

	@Override
	public JSONObject createTask(TaskModel taskModel) {
		MongoDatabase db = mongoClient.getDatabase(environment
				.getProperty("mongo.dataBase"));

		MongoCollection<BasicDBObject> coll = db.getCollection(
				environment.getProperty("mongo.taskCollection"),
				BasicDBObject.class);

		Gson gson = new Gson();
		
		JSONObject json = new JSONObject();
		
		BasicDBObject whereQuery = new BasicDBObject();
		whereQuery.put("taskTitle", taskModel.getTaskTitle());
		FindIterable<BasicDBObject> obj = coll.find(whereQuery);
		
		BasicDBObject basicGroupObj = (BasicDBObject) JSON.parse(gson
				.toJson(taskModel));
		
		if (obj.first() == null) {
			
			coll.insertOne(basicGroupObj);
			json.put("httpStatus", HttpStatus.OK);
			json.put("id", basicGroupObj.get("_id"));
			return json;
		}else{
			json.put("httpStatus", HttpStatus.FOUND);
			json.put("id", basicGroupObj.get("_id"));
			return json;
		}
	}

	@Override
	public TaskModel fetchTask(String taskName) {
		MongoDatabase db = mongoClient.getDatabase(environment
				.getProperty("mongo.dataBase"));

		MongoCollection<BasicDBObject> coll = db.getCollection(
				environment.getProperty("mongo.taskCollection"),
				BasicDBObject.class);
		BasicDBObject whereQuery = new BasicDBObject();
		whereQuery.put("taskTitle", taskName);

		FindIterable<BasicDBObject> obj = coll.find(whereQuery);
		TaskModel taskModel = new TaskModel();
		if (obj.first() != null) {
			taskModel = (TaskModel) (new Gson()).fromJson(obj.first().toString(),
					TaskModel.class);
		}
		return taskModel;
	}

	@Override
	public List<TaskModel> getMyTasks(String userEmail) {
		List<TaskModel> myTasks = new ArrayList<TaskModel>();
		MongoDatabase db = mongoClient.getDatabase(environment
				.getProperty("mongo.dataBase"));

		MongoCollection<BasicDBObject> coll = db.getCollection(
				environment.getProperty("mongo.taskCollection"),
				BasicDBObject.class);
		BasicDBObject whereQuery = new BasicDBObject();
		whereQuery.put("userEmail", userEmail);

		FindIterable<BasicDBObject> obj = coll.find(whereQuery);
		
		if (obj.first() != null) {
			for(BasicDBObject task: obj){
				TaskModel taskModel = new TaskModel();
				taskModel = (TaskModel) (new Gson()).fromJson(task.toString(),
						TaskModel.class);
				myTasks.add(taskModel);
			}
		}
		return myTasks;
	}

}
