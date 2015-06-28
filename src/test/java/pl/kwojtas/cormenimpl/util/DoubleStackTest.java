package pl.kwojtas.cormenimpl.util;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class DoubleStackTest {

    @Test
    public void shouldCreateDoubleStackOfGivenLength() {
        // given
        int length = 6;

        // when
        DoubleStack<String> doubleStack = DoubleStack.withLength(length);

        // then
        assertEquals(length, doubleStack.length);
        assertEquals(0, doubleStack.top1);
        assertEquals(length + 1, doubleStack.top2);
    }

}
