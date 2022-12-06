package shpp.com.app;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.InsertOneResult;
import jakarta.json.Json;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import shpp.com.model.Product;
import shpp.com.model.Shop;
import shpp.com.repo.ConnectToMongoDB;
import shpp.com.services.MyValidator;
import shpp.com.services.PojoGenerator;
import shpp.com.util.MyException;

import java.util.stream.Stream;

import static com.mongodb.client.model.Filters.eq;

public class MyApp {
    private static final Logger logger = LoggerFactory.getLogger(MyApp.class);
    private static final String DATABASE_NAME = "test";
    private static final String COLLECTION_NAME = "collection_test";

    public static void main(String[] args) throws MyException, JsonProcessingException {
        long startTime = System.currentTimeMillis();
        ConnectToMongoDB mongo = new ConnectToMongoDB();
        MongoDatabase database = mongo.getMongoClient().getDatabase(DATABASE_NAME);
        long executionTime = getTotalTime(startTime);
        logger.info("Connection time is: {}", executionTime);
        PojoGenerator pojoGenerator = new PojoGenerator();
        //генерація і валідація продуктів
        // передача продуктів в db
        fillDocument(pojoGenerator, database, COLLECTION_NAME);

//        addOneDocumentToDB(database, "test", product);
        mongo.getMongoClient().close();
        logger.info("Program time is: {}", getTotalTime(startTime));
        logger.info("Close app, successful!");
    }

    private static long getTotalTime(long startTime) {
        return System.currentTimeMillis() - startTime;
    }

    private static void showMessage(MongoDatabase database) {
        MongoCollection<Document> collection = database.getCollection("collection_test");
        Document doc = collection.find().first();
        logger.info(doc.toJson());
    }

    private static void addOneDocumentToDB(MongoDatabase database, String collectionName, Object object) {
        try {
            Document document = Document.parse(new ObjectMapper().writeValueAsString(object));
            database.getCollection(collectionName).insertOne(document);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private static void fillDocument(PojoGenerator pojoGenerator, MongoDatabase database, String collectionName){
        for (int i = 0; i < 1000; i++){
            Product product = pojoGenerator.createProduct();
            if(new MyValidator(product).complexValidator()){
                addOneDocumentToDB(database, collectionName, product);
                logger.info("add successful # {}", i);
            } else {
                i--;
            }
        }
        logger.info("generate document successful!");
    }
}