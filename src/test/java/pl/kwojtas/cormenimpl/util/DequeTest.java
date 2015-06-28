package pl.kwojtas.cormenimpl.util;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class DequeTest {

    @Test
    public void shouldCreateDequeOfGivenLength() {
        // given
        int length = 6;

        // when
        Deque<String> deque = Deque.withLength(length);

        // then
        assertEquals(length, deque.length);
        assertEquals(1, deque.head);
        assertEquals(1, deque.tail);
    }

}
