package pl.kwojtas.cormenimpl.datastructure;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class QueueTest {

    @Test
    public void shouldCreateQueueOfGivenLength() {
        int length = 6;

        Queue<String> queue = Queue.ofLength(length);

        assertEquals(length, queue.length);
        assertEquals(1, queue.head);
        assertEquals(1, queue.tail);
    }

}
