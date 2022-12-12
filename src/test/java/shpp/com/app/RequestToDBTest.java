package shpp.com.app;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import shpp.com.repo.ConnectToMongoDB;
import shpp.com.util.MyException;

import static org.junit.jupiter.api.Assertions.*;

class RequestToDBTest {
    @Test
    void addDocumentsToDBTest() throws MyException {
//        MongoDatabase database = Mockito.mock(MongoDatabase.class);
//        MongoCollection<Document> collection = Mockito.mock(MongoCollection.class);
//        RequestToDB request = Mockito.mock(RequestToDB.class);
//        Mockito.doCallRealMethod().when(request).addDocumentsToDB(database, collection);
//        request.addDocumentsToDB(database, collection);
//        Mockito.verify(request).addDocumentsToDB(database, collection);
    }

    @Test
    void searchTest() throws MyException {
//        MongoDatabase database = Mockito.mock(MongoDatabase.class);
//        MongoCollection<Document> collection = Mockito.mock(MongoCollection.class);
//        RequestToDB request = Mockito.mock(RequestToDB.class);
//        request.search(database, "category", collection);
//        Mockito.verify(request).search(database, "category", collection);
    }

    @Test
    void getTotalTimeTest() {
        long expected = System.currentTimeMillis();
        long actual = RequestToDB.getTotalTime(expected);
        assertNotEquals(expected, actual);
    }
}