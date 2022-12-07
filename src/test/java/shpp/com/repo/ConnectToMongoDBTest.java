package shpp.com.repo;

import com.mongodb.client.MongoClient;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import shpp.com.util.MyException;

import static org.junit.jupiter.api.Assertions.*;

class ConnectToMongoDBTest {

    @Test
    void getMongoClientTest() throws MyException {
        ConnectToMongoDB connect = new ConnectToMongoDB();
        MongoClient mongoClient = connect.getMongoClient();
        assertNotNull(connect);
        mongoClient.close();
    }
}