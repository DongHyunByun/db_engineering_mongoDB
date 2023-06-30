package org.de.mongodb.read;

import com.mongodb.client.*;
import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.de.mongodb.model.Product;

import java.time.format.DateTimeFormatter;

import static com.mongodb.MongoClientSettings.getDefaultCodecRegistry;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.regex;
import static com.mongodb.client.model.Indexes.descending;
import static com.mongodb.client.model.Projections.include;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public class Main {
    public static void main(String[] args) {
        CodecProvider pojoCodecProvider = PojoCodecProvider.builder().automatic(true).build();
        CodecRegistry codecRegistry = fromRegistries(getDefaultCodecRegistry(), fromProviders(pojoCodecProvider));

        String uri = "mongodb://localhost:27017";
        MongoClient mongoClient = MongoClients.create(uri);
        MongoDatabase mongoDatabase = mongoClient.getDatabase("de-mongodb").withCodecRegistry(codecRegistry);
        MongoCollection<Product> collection = mongoDatabase.getCollection("products", Product.class);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

//        MongoCursor<Product> cursor = collection.find(eq("price",10000)).cursor();
        MongoCursor<Product> cursor = collection.find(regex("name","shoes"))
                                                //.projection(include("name")) //view wanted value
                                                .sort(descending("price"))
                                                .cursor();
        while(cursor.hasNext()) {
            System.out.println(cursor.next());
        }

    }
}
