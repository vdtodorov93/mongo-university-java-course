package com.mongodb;

import static java.util.Arrays.*;
import java.util.List;

import org.bson.Document;

import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class HW_2_3_DeleteLowGrades {

	public static void main(String[] args) {
		double Eps = 0.00002;
		MongoClient client = new MongoClient();

        MongoDatabase database = client.getDatabase("students");
        final MongoCollection<Document> collection = database.getCollection("grades");
        System.out.println(collection.count());
        AggregateIterable<Document> result = collection.aggregate(asList(
        		new Document("$match", new Document("type", "homework")), 
        		new Document("$group", new Document("_id", "$student_id").append("score", new Document("$min", "$score")))));
        System.out.println(result.first());
        for(Document doc: result) {
        	Object id = doc.get("_id");
        	Double score = doc.getDouble("score");
        	System.out.println(id + " : " + score);
        	collection.deleteOne(new Document("student_id", id).append("score", score));//new Document("$lte", score + Eps)));
        }
        System.out.println(collection.count());

	}

}
