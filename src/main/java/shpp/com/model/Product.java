package shpp.com.model;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.bson.Document;

public class Product {

    @NotNull
    @Size(min = 5, max = 25)
    private String name;

    private String categoryID;

    @Min(value = 0)
    private double price;

    private Shop shop;

    public Product() {
        // it's empty
    }

    public Product setName(String name) {
        this.name = name;
        return this;
    }

    public Product setCategory(String categoryID) {
        this.categoryID = categoryID;
        return this;
    }

    public Product setPrice(double price) {
        this.price = price;
        return this;
    }

    public Product setShop(Shop shop) {
        this.shop = shop;
        return this;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return categoryID;
    }

    public Shop getShop() {
        return shop;
    }

    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", category=" + categoryID +
                ", price=" + price +
                ", shop=" + shop +
                '}';
    }
}
