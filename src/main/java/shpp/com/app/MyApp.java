package shpp.com.app;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mongodb.client.MongoDatabase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.event.Level;
import shpp.com.repo.ConnectToMongoDB;
import shpp.com.util.MyException;
import ch.qos.logback.classic.LoggerContext;

public class MyApp {
    private static final Logger logger = LoggerFactory.getLogger(MyApp.class);
    private static final String DATABASE_NAME = "test";
    private static final String COLLECTION_NAME = "collection_test";
    public static void main(String[] args) throws MyException {
        long startProgramTime = System.currentTimeMillis();
        long startTime = System.currentTimeMillis();
        ConnectToMongoDB mongo = new ConnectToMongoDB();
        mongo.getMongoClient().startSession().startTransaction();
        MongoDatabase database = mongo.getMongoClient().getDatabase(DATABASE_NAME);
        long connectionTime = RequestToDB.getTotalTime(startTime);
        // deleting a collection before creating it
        database.getCollection(COLLECTION_NAME).drop();
        // creating a collection
        startTime = System.currentTimeMillis();
        RequestToDB request = new RequestToDB();
        request.addDocumentsToDB(database, COLLECTION_NAME);
        long generationTime = RequestToDB.getTotalTime(startTime);
        // search by collection
        startTime = System.currentTimeMillis();
        String result = request.search(database, "category", COLLECTION_NAME);
        long requestTime = RequestToDB.getTotalTime(startTime);
        logger.info("_______________________________________________________________________________________________");
        logger.info("RESULT : {}", result);
        logger.info("Collection size is: {}", database.getCollection(COLLECTION_NAME).countDocuments());
        logger.info("Connection time is: {}", connectionTime);
        logger.info("Generation time is: {}", generationTime);
        logger.info("Request time is: {}", requestTime);
        logger.info("Program time is: {}", RequestToDB.getTotalTime(startProgramTime));
        logger.info("_______________________________________________________________________________________________");
        mongo.getMongoClient().close();
        logger.info("Close app, successful!");
    }
}