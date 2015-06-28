package pl.kwojtas.cormenimpl.util;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class StackTest {

    @Test
    public void shouldCreateStackOfGivenLength() {
        // given
        int length = 6;

        // when
        Stack<String> stack = Stack.withLength(length);

        // then
        assertEquals(length, stack.length);
        assertEquals(0, stack.top);
    }

}
