package pl.kwojtas.cormenimpl.datastructure;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class DequeTest {

    @Test
    public void shouldCreateDequeOfGivenLength() {
        int length = 6;

        Deque<String> deque = Deque.withLength(length);

        assertEquals(length, deque.length);
        assertEquals(1, deque.head);
        assertEquals(1, deque.tail);
    }

}
