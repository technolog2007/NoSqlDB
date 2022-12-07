package shpp.com.model;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ProductTest {

    private static Validator validator;

    @BeforeAll
    public static void setUpValidator() {
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            validator = factory.getValidator();
        }
    }

    @Test
    void setNameSetValueAndReturnIt() {
        String actual = "Ноутбук";
        Product product = new Product().setName(actual);
        String expected = product.getName();
        assertEquals(expected, actual);
    }

    @Test
    void productNameSetNullAndIsInvalid(){
        Product product = new Product().setCategory("Electronica").setName(null).setPrice(100);
        Set<ConstraintViolation<Product>> constraintViolations = validator.validate(product);
        assertEquals(1, constraintViolations.size());
        assertEquals("{jakarta.validation.constraints.NotNull.message}",
                constraintViolations.iterator().next().getMessageTemplate());
    }

    @Test
    void productNameSetInvalidName(){
        Product product = new Product().setCategory("Electronica").
                setName("Notebook Asus Vivo 15 Pro Intel Core 5").setPrice(100);
        Set<ConstraintViolation<Product>> constraintViolations = validator.validate(product);
        assertEquals(1, constraintViolations.size());
        assertEquals("{jakarta.validation.constraints.Size.message}",
                constraintViolations.iterator().next().getMessageTemplate());
    }

    @Test
    void setCategorySetValueAndReturnIt() {
        String actual = "Electronica";
        Product product = new Product().setCategory(actual);
        String expected = product.getCategory();
        assertEquals(expected, actual);
    }

    @Test
    void setPriceSetValueAndReturnIt() {
        int actual = 100;
        Product product = new Product().setPrice(actual);
        double expected = product.getPrice();
        assertEquals(expected, actual);
    }

    @Test
    void productPriceSetNullAndIsInvalid(){
        double expected = -1.1;
        Product product = new Product().setCategory("Electronica").setName("ноутбук").setPrice(expected);
        Set<ConstraintViolation<Product>> constraintViolations = validator.validate(product);
        assertEquals(1, constraintViolations.size());
        assertEquals("{jakarta.validation.constraints.Min.message}",
                constraintViolations.iterator().next().getMessageTemplate());
    }

    @Test
    void getNameSetValueAndReturnIt() {
        String expected = "Ноутбук";
        Product product = new Product().setName(expected);
        String actual = product.getName();
        assertEquals(expected, actual);
    }

    @Test
    void getCategorySetValueAndReturnIt() {
        String expected = "Electronica";
        Product product = new Product().setCategory(expected);
        String actual = product.getCategory();
        assertEquals(expected, actual);
    }

    @Test
    void getPriceSetValueAndReturnIt() {
        int expected = 100;
        Product product = new Product().setPrice(expected);
        double actual = product.getPrice();
        assertEquals(expected, actual);
    }

    @Test
    void testToStringSetValuesInObjectAndReturnStringIsEquals() {
        String expected = "Product{name='Ноутбук', category=Electronica, price=100.0}";
        Product product = new Product().setCategory("Electronica").setName("Ноутбук").setPrice(100);
        String actual = product.toString();
        assertEquals(expected, actual);
    }
}