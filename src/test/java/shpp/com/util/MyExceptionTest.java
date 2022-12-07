package shpp.com.util;

import org.junit.jupiter.api.Test;
import shpp.com.util.MyException;

import static org.junit.jupiter.api.Assertions.assertTrue;

class MyExceptionTest {

    @Test
    void myExceptionTest() {
        MyException expected = new MyException("message");
        assertTrue(expected.getMessage().contains("message"));
    }

}