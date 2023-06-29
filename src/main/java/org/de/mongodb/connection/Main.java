package org.de.mongodb.connection;


import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import static com.mongodb.client.model.Filters.eq;

public class Main {
    public static void main(String[] args) {
        String uri = "mongodb://localhost:27017";
        MongoClient mongoClient = MongoClients.create(uri);
        MongoDatabase mongoDatabase = mongoClient.getDatabase("mySecondDatabase");
        MongoCollection<Document> collection = mongoDatabase.getCollection("movie_korean");

        Document document = collection.find(eq("title","Jurassic World: Fallen Kingdom")).first();
        System.out.println(document.toJson());
    }
}
