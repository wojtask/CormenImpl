package pl.kwojtas.cormenimpl.util;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class HashTableWithFreeListTest {

    @Test
    public void shouldCreateEmptyHashTableWithFreeList() {
        // given
        int length = 6;
        HashFunction h = new HashFunction() {
            @Override
            public int compute(int key) {
                return key % length;
            }
        };

        // when
        HashTableWithFreeList<String> hashTableWithFreeList = HashTableWithFreeList.withLengthAndHashFunction(length, h);

        // then
        assertEquals(length, hashTableWithFreeList.length);
        assertEquals(h, hashTableWithFreeList.h);
        assertEquals(Integer.valueOf(0), hashTableWithFreeList.free);
    }

}

