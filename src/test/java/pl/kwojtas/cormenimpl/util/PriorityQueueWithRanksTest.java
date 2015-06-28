package pl.kwojtas.cormenimpl.util;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PriorityQueueWithRanksTest {

    @Test
    public void shouldCreateEmptyPriorityQueueOfGivenLength() {
        // given
        int length = 5;

        // when
        PriorityQueueWithRanks<String> priorityQueueWithRanks = new PriorityQueueWithRanks<>(length);

        // then
        assertEquals(length, priorityQueueWithRanks.length);
        assertEquals(1, priorityQueueWithRanks.getCurrentRank());
    }

    @Test
    public void shouldIncrementRank() {
        // given
        PriorityQueueWithRanks<String> priorityQueueWithRanks = new PriorityQueueWithRanks<>(5);

        // when
        int rank = priorityQueueWithRanks.getCurrentRank();

        // then
        assertEquals(rank + 1, priorityQueueWithRanks.getCurrentRank());
    }

}
