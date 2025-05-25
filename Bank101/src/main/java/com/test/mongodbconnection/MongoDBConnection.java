package com.test.mongodbconnection;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class MongoDBConnection {
	
	private static final String CONNECTION = "mongodb://localhost:27017";
    private static final String DATABASE_NAME = "Banking";

    private static MongoDatabase database;

    public static MongoDatabase getDatabase() {
        if (database == null) {
            MongoClient client = MongoClients.create(CONNECTION);
            database = client.getDatabase(DATABASE_NAME);
        }
        return database;
    }

}
