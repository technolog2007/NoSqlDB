package shpp.com.app;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import shpp.com.services.MyValidator;
import shpp.com.services.PojoGenerator;
import shpp.com.util.MyException;
import shpp.com.util.PropertiesLoader;

import java.util.Properties;
import java.util.stream.Stream;

public class RequestToDB {
    private static final Logger logger = LoggerFactory.getLogger(MyApp.class);
    private static final String PROPERTIES_FILE = "app.properties";


    public RequestToDB() {
        //
    }

    public void addDocumentsToDB(MongoDatabase database, String collection) throws MyException {
        long startTime = System.currentTimeMillis();
        PojoGenerator pojoGenerator = new PojoGenerator();
        fillDocument(pojoGenerator, database, collection);
        logger.info("Generate time is: {}", getTotalTime(startTime));
    }

    public void search(MongoDatabase database, String parameter, String collection) {
        long startTime = System.currentTimeMillis();
        String category = getSystemProperty(parameter);
        Document requestMax = new Document("quantity","-1");
        FindIterable<Document> result = database.getCollection(collection).find().sort(requestMax);
        for(Document doc : result) {
            logger.info("Resul is : {}", doc.toJson());
        }
        logger.info("Generate time is: {}", getTotalTime(startTime));
    }

    private long getTotalTime(long startTime) {
        return System.currentTimeMillis() - startTime;
    }

    private void addOneDocumentToDB(MongoDatabase database, String collectionName, Object object) {
        try {
            Document document = Document.parse(new ObjectMapper().writeValueAsString(object));
            database.getCollection(collectionName).insertOne(document);
            logger.info("Document add successful!");
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private void fillDocument(PojoGenerator pojoGenerator, MongoDatabase database, String collectionName) throws MyException {
        int numberOfDocuments = Integer.parseInt(getProperty("numberOfProducts"));
        Stream.generate(pojoGenerator::createRemains).
                filter((remains) -> new MyValidator(remains).complexValidator()).
                limit(numberOfDocuments).forEach((remains) -> addOneDocumentToDB(database, collectionName, remains));
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
