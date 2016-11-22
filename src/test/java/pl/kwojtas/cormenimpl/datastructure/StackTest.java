package pl.kwojtas.cormenimpl.datastructure;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class StackTest {

    @Test
    public void shouldCreateStackOfGivenLength() {
        int length = 6;

        Stack<String> stack = Stack.ofLength(length);

        assertEquals(length, stack.length);
        assertEquals(0, stack.top);
    }

}
