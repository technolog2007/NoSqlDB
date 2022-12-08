package shpp.com.app;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import shpp.com.model.Remains;
import shpp.com.services.MyValidator;
import shpp.com.services.PojoGenerator;
import shpp.com.util.MyException;
import shpp.com.util.PropertiesLoader;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;
import java.util.stream.Stream;

public class RequestToDB {
    private static final Logger logger = LoggerFactory.getLogger(RequestToDB.class);
    private static final String PROPERTIES_FILE = "app.properties";


    public RequestToDB() {
        //
    }

    public void addDocumentsToDB(MongoDatabase database, String collection) throws MyException {
        long startTime = System.currentTimeMillis();
        PojoGenerator pojoGenerator = new PojoGenerator();
        fillDatabaseWithObjects(pojoGenerator, database, collection);
        logger.info("Generate time is: {}", getTotalTime(startTime));
    }

    public String search(MongoDatabase database, String parameter, String collection) {
        String category = getSystemProperty(parameter);
        Document requestMax = new Document("quantity", -1);
        Document requestCategory = new Document("product.category", category);
        FindIterable<Document> request = database.getCollection(collection).find(requestCategory).sort(requestMax).limit(1);
        return request.first().toJson();
    }

    public static long getTotalTime(long startTime) {
        return System.currentTimeMillis() - startTime;
    }

    private void addOneDocumentToDB(MongoDatabase database, String collectionName, Object object) {
        try {
            Document document = Document.parse(new ObjectMapper().writeValueAsString(object));
            database.getCollection(collectionName).insertOne(document);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private void addManyDocumentToDB(MongoDatabase database, String collectionName, Object object, int counter, List<Document> list) {
        try {
            Document document = Document.parse(new ObjectMapper().writeValueAsString(object));
            list.add(document);
            if (counter % 1000 == 0) {
                database.getCollection(collectionName).insertMany(list);
                list = new ArrayList<>();
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private void fillDatabaseWithObjects(PojoGenerator pojoGenerator, MongoDatabase database, String collectionName) throws MyException {
        int numberOfDocuments = Integer.parseInt(getProperty("numberOfProducts"));
        int counter = 0;
        List<Document> list = new ArrayList<>();
        for (int i = 0; i < numberOfDocuments; i++) {
            Remains remains = pojoGenerator.createRandomValidRemains();
            if (new MyValidator(remains).complexValidator()) {
                try {
                    Document document = Document.parse(new ObjectMapper().writeValueAsString(remains));
                    list.add(document);
                    if (counter % 10000 == 0) {
                        database.getCollection(collectionName).insertMany(list);
                        list.clear();
                        logger.info("Send a pack of # {}", i);
                    }
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
                counter++;
            } else {
                i--;
            }
        }
        database.getCollection(collectionName).insertMany(list);
        logger.info("generate document successful!");
    }

    /**
     * The method loads properties from the properties file and returns properties
     *
     * @return - properties
     */
    private Properties loadProperties() {
        return new PropertiesLoader().loadProperties(PROPERTIES_FILE);
    }

    /**
     * The method returns the value for the corresponding key property
     *
     * @param property - open the key
     * @return - the value of the key property
     * @throws MyException -
     */
    private String getProperty(String property) throws MyException {
        String url = loadProperties().getProperty(property);
        if (url != null) {
            return url;
        } else {
            throw new MyException("Sorry! Please enter " + property + " required for connection!");
        }
    }

    /**
     * The method returns the value for the "category" system parameter, or the default value
     *
     * @return - value for the "category" parameter
     */
    private String getSystemProperty(String category) {
        if (System.getProperty(category) != null) {
            return System.getProperty(category);
        } else {
            return "Electronics";
        }
    }
}
