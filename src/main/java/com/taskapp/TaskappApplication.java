package com.taskapp;

import java.time.LocalDate;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@SpringBootApplication
@ComponentScan({"com.taskapp"})
@PropertySource({ "classpath:persistence-mongo.properties" })
public class TaskappApplication{

	public static void main(String[] args) {
		SpringApplication.run(TaskappApplication.class, args);
	}
	
	@Autowired
	private Environment env;
	
	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select().apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.any())
				.build()
				.directModelSubstitute(LocalDate.class, String.class)
				.genericModelSubstitutes(ResponseEntity.class);
	}

	@Bean(name = "dataSourceClient")
	public MongoClient dataSource() {
		/*
		 * return new MongoClient(env.getProperty("mongo.url"),
		 * Integer.valueOf(env.getProperty("mongo.port")));
		 */

		/*String mongoUserName = env.getProperty("mongo.userName");

		String mongoPassword = env.getProperty("mongo.password");*/

		String host = env.getProperty("mongo.url").toString();

		Object portOb = env.getProperty("mongo.port");
		Integer port = Integer.parseInt(portOb.toString());

		String db = env.getProperty("mongo.dataBase").toString();

		/*if (mongoPassword == null) {*/
			return new MongoClient(env.getProperty("mongo.url"), Integer.valueOf(env.getProperty("mongo.port")));
		/*}*/
		/*MongoCredential credential = MongoCredential.createCredential(mongoUserName, db, mongoPassword.toCharArray());
		MongoClient client = new MongoClient(new ServerAddress(host, port), Arrays.asList(credential));
		return client;*/
	}
}
