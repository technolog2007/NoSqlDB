package shpp.com.util;

import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class MyFileLoaderTest {

    @Test
    void createInputDataFromFileNotEmptyTest() throws MyException {
        MyFileLoader loader = new MyFileLoader();
        loader.createInputDataFromFile("streets.txt");
        List<String> listOfStreets = loader.getStreets();
        int expected = 12;
        int actual = listOfStreets.size();
        assertEquals(expected, actual);
    }

    @Test
    void createInputDataFromFileInvalidFileTest() {
        MyFileLoader loader = new MyFileLoader();
        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> loader.createInputDataFromFile("streetFail.txt"),
                "ERROR! File not found! Please input correct file name!");
        assertEquals("ERROR! File not found! Please input correct file name!", exception.getMessage());
    }

    @Test
    void getProductsTestIsNotEmpty() throws MyException {
        MyFileLoader loader = new MyFileLoader();
        loader.createInputDataFromFile("products.txt");
        List<List<String>> products = loader.getProducts();
        int actual = products.size();
        assertTrue(actual > 0);
    }

    @Test
    void getStreetsTestIsNotEmpty() throws MyException {
        MyFileLoader loader = new MyFileLoader();
        loader.createInputDataFromFile("streets.txt");
        List<String> streets = loader.getStreets();
        int actual = streets.size();
        assertTrue(actual > 0);
    }

    @Test
    void getCitiesTestIsNotEmpty() throws MyException {
        MyFileLoader loader = new MyFileLoader();
        loader.createInputDataFromFile("cities.txt");
        List<String> cities = loader.getCities();
        int actual = cities.size();
        assertTrue(actual > 0);
    }

    @Test
    void getCategoryTestIsNotEmpty() throws MyException {
        MyFileLoader loader = new MyFileLoader();
        loader.createInputDataFromFile("products.txt");
        List<String> categories = loader.getCategory();
        int actual = categories.size();
        assertTrue(actual > 0);
    }

    @Test
    void changerExceptionTest(){
        MyFileLoader loader = new MyFileLoader();
        MyException exception = assertThrows(
                MyException.class,
                () -> loader.createInputDataFromFile("task.txt"),
                "ERROR! Please, rename files with input data, which they are consist " +
                        "\"cities\", \"street\", \"products\"");
        assertEquals("ERROR! Please, rename files with input data, which they are consist " +
                "\"cities\", \"street\", \"products\"", exception.getMessage());
    }
}