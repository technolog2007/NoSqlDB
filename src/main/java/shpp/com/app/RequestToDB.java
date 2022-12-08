package shpp.com.app;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import shpp.com.model.Remains;
import shpp.com.services.MyValidator;
import shpp.com.services.PojoGenerator;
import shpp.com.util.MyException;
import shpp.com.util.PropertiesLoader;

import javax.print.Doc;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class RequestToDB {
    private static final Logger logger = LoggerFactory.getLogger(RequestToDB.class);
    private static final String PROPERTIES_FILE = "app.properties";
    private static final int NUMBER_OF_DOCUMENTS_IN_BATCH = 50000;


    public RequestToDB() {
        //
    }

    public void addDocumentsToDB(MongoDatabase database, MongoCollection<Document> collection) throws MyException {
        long startTime = System.currentTimeMillis();
        PojoGenerator pojoGenerator = new PojoGenerator();
        fillDatabaseWithObjects(pojoGenerator, collection);
        logger.info("Generate time is: {}", getTotalTime(startTime));
    }

    public String search(MongoDatabase database, String parameter, MongoCollection<Document> collection) {
        String category = getSystemProperty(parameter);
        Document requestMax = new Document("quantity", -1);
        Document requestCategory = new Document("product.category", category);
        FindIterable<Document> request = collection.find(requestCategory).sort(requestMax).limit(1);
        return request.first().toJson();
    }

    public static long getTotalTime(long startTime) {
        return System.currentTimeMillis() - startTime;
    }

    /**
     * The method generates random objects Overflows and refills the database in batches of N pcs
     * @param pojoGenerator - random POJO generator
//     * @param database - mongodb database
//     * @param collectionName - collection name
     * @throws MyException -
     */
    private void fillDatabaseWithObjects(PojoGenerator pojoGenerator, MongoCollection<Document> collection) throws MyException {
        int numberOfDocuments = Integer.parseInt(getProperty("numberOfProducts"));
        int counter = 0;
        List<Document> list = new ArrayList<>();
        for (int i = 0; i < numberOfDocuments; i++) {
            Remains remains = pojoGenerator.createRandomValidRemains();
            if (new MyValidator(remains).complexValidator()) {
                try {
                    Document document = Document.parse(new ObjectMapper().writeValueAsString(remains));
                    list.add(document);
                    if (counter % NUMBER_OF_DOCUMENTS_IN_BATCH == 0) {
                        collection.insertMany(list);
//                        database.getCollection(collectionName).insertMany(list);
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
        collection.insertMany(list);
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
