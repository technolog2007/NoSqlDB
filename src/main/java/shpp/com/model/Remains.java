package shpp.com.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class Remains {
    @NotNull
    Product product;
    @NotNull
    Shop shop;
    @Min(value = 0)
    float quantity;

    public Remains() {
        //
    }

    public Product getProduct() {
        return product;
    }

    public Remains setProduct(Product product) {
        this.product = product;
        return this;
    }

    public Shop getShop() {
        return shop;
    }

    public Remains setShop(Shop shop) {
        this.shop = shop;
        return this;
    }

    public float getQuantity() {
        return quantity;
    }

    public Remains setQuantity(float quantity) {
        this.quantity = quantity;
        return this;
    }

    @Override
    public String toString() {
        return "Remains{" +
                "product=" + product +
                ", shop=" + shop +
                ", quantity=" + quantity +
                '}';
    }
}
