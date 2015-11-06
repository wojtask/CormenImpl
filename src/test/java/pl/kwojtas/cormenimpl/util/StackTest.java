package pl.kwojtas.cormenimpl.util;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class StackTest {

    @Test
    public void shouldCreateStackOfGivenLength() {
        int length = 6;

        Stack<String> stack = Stack.withLength(length);

        assertEquals(length, stack.length);
        assertEquals(0, stack.top);
    }

}
