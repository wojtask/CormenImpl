package pl.kwojtas.cormenimpl.util;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class QueueTest {

    @Test
    public void shouldCreateQueueOfGivenLength() {
        int length = 6;

        Queue<String> queue = Queue.withLength(length);

        assertEquals(length, queue.length);
        assertEquals(1, queue.head);
        assertEquals(1, queue.tail);
    }

}
