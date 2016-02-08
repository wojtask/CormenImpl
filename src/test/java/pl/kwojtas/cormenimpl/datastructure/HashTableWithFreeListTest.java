package pl.kwojtas.cormenimpl.datastructure;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class HashTableWithFreeListTest {

    @Test
    public void shouldCreateEmptyHashTableWithFreeList() {
        int length = 6;
        HashFunction h = key -> key % length;

        HashTableWithFreeList<String> hashTableWithFreeList = HashTableWithFreeList.withLengthAndHashFunction(length, h);

        assertEquals(length, hashTableWithFreeList.length);
        assertEquals(h, hashTableWithFreeList.h);
        assertEquals(0, hashTableWithFreeList.F);
    }

}
