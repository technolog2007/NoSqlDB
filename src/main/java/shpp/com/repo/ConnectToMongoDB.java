package shpp.com.repo;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import shpp.com.util.MyException;
import shpp.com.util.PropertiesLoader;

import java.util.Properties;

public class ConnectToMongoDB {
    private final static Logger logger = LoggerFactory.getLogger(ConnectToMongoDB.class);
    private static final String PROPERTIES_FILE = "app.properties";
    private static final String PROPERTY_URI = "uri";
    private final MongoClient mongoClient;

    public ConnectToMongoDB() throws MyException {
        String uri = getProperty(PROPERTY_URI);
        mongoClient = MongoClients.create(uri);
        logger.info("Connect is successful!");
    }

    /**
     * Метод повертає з'єднання із базою даних в облаці для клієнта
     * @return MongoClient
     */
    public MongoClient getMongoClient() {
        return mongoClient;
    }

    /**
     * Method to load and restore all properties from a property file
     *
     * @return - property
     */
    private static Properties loadProperties() {
        return new PropertiesLoader().loadProperties(PROPERTIES_FILE);
    }

    /**
     * The method returns the selected property as a string
     *
     * @param property - property name
     * @return - property values from the properties file
     */
    private static String getProperty(String property) throws MyException {
        String url = loadProperties().getProperty(property);
        if (url != null) {
            return url;
        } else {
            throw new MyException("Sorry! Please enter " + property + " required for connection!");
        }
    }


}
