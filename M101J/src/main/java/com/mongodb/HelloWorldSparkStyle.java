package com.mongodb;

import spark.Request;
import spark.Route;
import spark.Spark;

public class HelloWorldSparkStyle {
	public static void main(String[] args) {
		Spark.get(new Route("/") {

			@Override
			public Object handle(Request arg0, spark.Response arg1) {
				return "Hello world from Spark";
			}
			
		});
	}
}
