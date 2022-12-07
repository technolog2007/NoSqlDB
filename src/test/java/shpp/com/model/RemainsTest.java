package shpp.com.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RemainsTest {

    @Test
    void getProductSetAndGetValue() {
        Product product = new Product().setName("Notebook apple").setCategory("Electronica").setPrice(100);
        Remains remains = new Remains().setProduct(product);
        Product actual = remains.getProduct();
        assertEquals(product, actual);
    }

    @Test
    void setProductSetAndReturnValue() {
        Product actual = new Product().setName("Notebook apple").setCategory("Electronica").setPrice(100);
        Remains remains = new Remains().setProduct(actual);
        Product expected = remains.getProduct();
        assertEquals(expected, actual);
    }

    @Test
    void getShopSetAndGetValue() {
        Shop shop = new Shop().setName("Epicenter № 333").setCity("Lviv").setLocation("str. Chernobaivka 96/6");
        Remains remains = new Remains().setShop(shop);
        Shop actual = remains.getShop();
        assertEquals(shop, actual);
    }

    @Test
    void setShopSetAndReturnValue() {
        Shop actual = new Shop().setName("Epicenter № 333").setCity("Lviv").setLocation("str. Chernobaivka 96/6");
        Remains remains = new Remains().setShop(actual);
        Shop expected = remains.getShop();
        assertEquals(expected, actual);
    }

    @Test
    void getQuantitySetAndGetValue() {
        float expected = 100;
        Remains remains = new Remains().setQuantity(expected);
        float actual = remains.getQuantity();
        assertEquals(expected, actual);
    }

    @Test
    void setQuantitySetAndReturnValue() {
        float actual = 100;
        Remains remains = new Remains().setQuantity(actual);
        float expected = remains.getQuantity();
        assertEquals(expected, actual);
    }

    @Test
    void testToStringSetValuesInObjectAndReturnStringIsEquals() {
        Product product = new Product().setName("Bread").setCategory("Food").setPrice(22);
        Shop shop = new Shop().setName("Epicenter № 555").setCity("Sumy").setLocation("str. Chernobaivka 777");
        Remains remains = new Remains().setProduct(product).setShop(shop).setQuantity(100);
        String expected = "Remains{product=Product{name='Bread', category=Food, price=22.0}, " +
                "shop=Shop{name='Epicenter № 555', location='str. Chernobaivka 777', city='Sumy'}, " +
                "quantity=100.0}";
        String actual = remains.toString();
        assertEquals(expected, actual);
    }
}