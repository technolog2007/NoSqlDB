package shpp.com.services;

import shpp.com.model.Product;
import shpp.com.model.Remains;
import shpp.com.model.Shop;
import shpp.com.util.MyException;
import shpp.com.util.MyFileLoader;

import java.util.List;
import java.util.Random;

public class PojoGenerator {
    private static final int UPPER_GENERATE_BOUND = 1000;
    private final List<String> listOfCities;
    private final List<String> listOfStreets;
    private final List<List<String>> listOfProducts;
    private static final String STREETS_FILE = "streets.txt";
    private static final String CITIES_FILE = "cities.txt";
    private static final String PRODUCTS_FILE = "products.txt";

    public PojoGenerator() throws MyException {
        MyFileLoader loader = loadInputData();
        this.listOfCities = loader.getCities();
        this.listOfStreets = loader.getStreets();
        this.listOfProducts = loader.getProducts();
    }

    /**
     * The method loads input data from files containing streets, cities, products and their properties
     *
     * @return - MyFileLoader
     * @throws MyException -
     */
    private MyFileLoader loadInputData() throws MyException {
        MyFileLoader loader = new MyFileLoader();
        loader.createInputDataFromFile(STREETS_FILE);
        loader.createInputDataFromFile(CITIES_FILE);
        loader.createInputDataFromFile(PRODUCTS_FILE);
        return loader;
    }

    /**
     * The method creates a store object from the fields name, city and location
     *
     * @return - Shop object
     */
    private Shop createRandomShop() {
        String shopName = "Epicenter № " + getRandomInt(UPPER_GENERATE_BOUND);
        String city = listOfCities.get(getRandomInt(listOfCities.size()));
        String location = "city " + city + ", str. " +
                listOfStreets.get(getRandomInt(listOfStreets.size())) +
                ", " + getRandomInt(UPPER_GENERATE_BOUND);
        return new Shop().setName(shopName).setCity(city).setLocation(location);
    }

    /**
     * The method creates a random shop, validates it and returns a valid Shop object
     * @return - valid Shop
     */
    private Shop getValidShop() {
        Shop shop = createRandomShop();
        boolean flag = new MyValidator(shop).complexValidator();
        while (!flag) {
            shop = createRandomShop();
            flag = new MyValidator(shop).complexValidator();
        }
        return shop;
    }

    /**
     * The method creates a product object from the fields name, categoryID, price
     *
     * @return - Product object
     */
    private Product createRandomProduct() {
        List<String> temp = listOfProducts.get(1 + getRandomInt(listOfProducts.size() - 1));
        String name = temp.get(2) + ", art. # " + getRandomInt(UPPER_GENERATE_BOUND);
        return new Product().
                setCategory(temp.get(1)).
                setName(name).
                setPrice(new Random().nextDouble() * UPPER_GENERATE_BOUND);
    }

    /**
     * The method creates and validates and returns random valid Product object
     * @return - valid Product object
     */
    private Product getValidProduct() {
        Product product = createRandomProduct();
        boolean flag = new MyValidator(product).complexValidator();
        while (!flag) {
            product = createRandomProduct();
            flag = new MyValidator(product).complexValidator();
        }
        return product;
    }

    /**
     * The method generates a random integer from 1 to upperBound
     *
     * @param upperBound - upper limit of integer generation
     * @return - number generated within specified limits
     */
    private int getRandomInt(int upperBound) {
        return 1 + new Random().nextInt(upperBound - 1);
    }

    /**
     * The method creates and returns a valid random Remainder object
     * @return - valid Remains
     */
    public Remains createRandomValidRemains() {
        return new Remains().
                setProduct(getValidProduct()).
                setQuantity(getRandomInt(UPPER_GENERATE_BOUND)).
                setShop(getValidShop());
    }
}
