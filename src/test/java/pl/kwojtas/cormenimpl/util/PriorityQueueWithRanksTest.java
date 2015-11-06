package pl.kwojtas.cormenimpl.util;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PriorityQueueWithRanksTest {

    @Test
    public void shouldCreateEmptyPriorityQueueOfGivenLength() {
        int length = 5;

        PriorityQueueWithRanks<String> priorityQueueWithRanks = new PriorityQueueWithRanks<>(length);

        assertEquals(length, priorityQueueWithRanks.length);
        assertEquals(1, priorityQueueWithRanks.getCurrentRank());
    }

    @Test
    public void shouldIncrementRank() {
        PriorityQueueWithRanks<String> priorityQueueWithRanks = new PriorityQueueWithRanks<>(5);

        int rank = priorityQueueWithRanks.getCurrentRank();

        assertEquals(rank + 1, priorityQueueWithRanks.getCurrentRank());
    }

}
