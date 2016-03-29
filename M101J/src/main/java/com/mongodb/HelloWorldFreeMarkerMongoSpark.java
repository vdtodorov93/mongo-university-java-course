package com.mongodb;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import org.bson.Document;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import freemarker.template.Configuration;
import freemarker.template.Template;
import spark.Request;
import spark.Route;
import spark.Spark;

public class HelloWorldFreeMarkerMongoSpark {
	public static void main(String[] args) {
		final Configuration configuration = new Configuration();
		configuration.setClassForTemplateLoading(HelloWorldSparkFreemarkerStyle.class, "/");

		MongoClient client = new MongoClient();
		MongoDatabase database = client.getDatabase("course");
		MongoCollection<Document> collection = database.getCollection("hello");
		collection.drop();
		
		collection.insertOne(new Document("name", "MongoDB"));
		
		Spark.get(new Route("/") {

			@Override
			public Object handle(Request arg0, spark.Response arg1) {
				StringWriter writer = new StringWriter();

				try {
					Template helloTemplate = configuration.getTemplate("hello.ftl");
					Document doc = collection.find().first();

					helloTemplate.process(doc, writer);
				} catch (Exception e) {
					halt(500);
					e.printStackTrace();
				}

				return writer;
			}

		});
	}
}
