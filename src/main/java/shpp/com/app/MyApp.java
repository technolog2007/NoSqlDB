package shpp.com.app;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import shpp.com.model.Product;
import shpp.com.repo.ConnectToMongoDB;
import shpp.com.util.MyException;

public class MyApp {
    private static final Logger logger = LoggerFactory.getLogger(MyApp.class);
    private static final String COLLECTION_NAME = "collection_test";

    public static void main(String[] args) throws MyException, JsonProcessingException {
        ConnectToMongoDB mongo = new ConnectToMongoDB();
        MongoDatabase database = mongo.getMongoClient().getDatabase("test");

        Product product = new Product().setName("Epicenter № 333").setCategoryID(1).setPrice(100);

        addOneDocumentToDB(database, "test", product);
        mongo.getMongoClient().close();
        logger.info("Close app, successful!");
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
}