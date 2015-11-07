package pl.kwojtas.cormenimpl.util;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class DoubleStackTest {

    @Test
    public void shouldCreateDoubleStackOfGivenLength() {
        int length = 6;

        DoubleStack<String> doubleStack = DoubleStack.withLength(length);

        assertEquals(length, doubleStack.length);
        assertEquals(0, doubleStack.leftTop);
        assertEquals(length + 1, doubleStack.rightTop);
    }

}
