package shpp.com.services;

import org.junit.jupiter.api.Test;
import shpp.com.model.Remains;
import shpp.com.util.MyException;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class PojoGeneratorTest {

    @Test
    void createRandomValidRemainsNotNull() throws MyException {
        PojoGenerator pojoGenerator = new PojoGenerator();
        Remains remains = pojoGenerator.createRandomValidRemains();
        assertNotNull(remains);
    }
}