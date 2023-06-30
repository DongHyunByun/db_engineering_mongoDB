package org.de.mongodb.aggregation;

import com.mongodb.client.*;
import com.mongodb.client.model.Accumulators;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Sorts;
import org.bson.Document;
import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import java.time.format.DateTimeFormatter;
import java.util.Arrays;

import static com.mongodb.MongoClientSettings.getDefaultCodecRegistry;
import static com.mongodb.client.model.Filters.gt;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public class Aggregation {
    public static void main(String[] args) {
        CodecProvider pojoCodecProvider = PojoCodecProvider.builder().automatic(true).build();
        CodecRegistry codecRegistry = fromRegistries(getDefaultCodecRegistry(), fromProviders(pojoCodecProvider));

        String uri = "mongodb://localhost:27017";
        MongoClient mongoClient = MongoClients.create(uri);
        MongoDatabase mongoDatabase = mongoClient.getDatabase("de-mongodb").withCodecRegistry(codecRegistry);
        MongoCollection<Document> collection = mongoDatabase.getCollection("products");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        MongoCursor<Document> cursor = collection.aggregate(
                Arrays.asList(
                    Aggregates.match(gt("price",10000)),
                    Aggregates.group("$name", Accumulators.avg("avg_price","$price")),
                    Aggregates.sort(Sorts.descending("avg_price"))
                )
        ).iterator();

        while(cursor.hasNext()) {
            System.out.println(cursor.next());
        }
    }
}
