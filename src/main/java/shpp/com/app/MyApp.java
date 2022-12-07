package shpp.com.app;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mongodb.client.MongoDatabase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import shpp.com.repo.ConnectToMongoDB;
import shpp.com.util.MyException;

import static com.mongodb.client.model.Filters.eq;

public class MyApp {
    private static final Logger logger = LoggerFactory.getLogger(MyApp.class);
    private static final String DATABASE_NAME = "test";
    private static final String COLLECTION_NAME = "collection_test";

    public static void main(String[] args) throws MyException, JsonProcessingException {
        long startTime = System.currentTimeMillis();
        ConnectToMongoDB mongo = new ConnectToMongoDB();
        MongoDatabase database = mongo.getMongoClient().getDatabase(DATABASE_NAME);
        logger.info("Connection time is: {}", getTotalTime(startTime));
        // видалення колекції перед створенням
        database.getCollection(COLLECTION_NAME).drop();
        long generateTime = System.currentTimeMillis();
        // створення колекції
        RequestToDB request = new RequestToDB();
        request.addDocumentsToDB(database, COLLECTION_NAME);
        request.search(database, "category", COLLECTION_NAME);
        // пошук по колекції
        mongo.getMongoClient().close();
        logger.info("Program time is: {}", getTotalTime(startTime));
        logger.info("Close app, successful!");
    }

    private static long getTotalTime(long startTime) {
        return System.currentTimeMillis() - startTime;
    }
}