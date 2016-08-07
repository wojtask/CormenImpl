package pl.kwojtas.cormenimpl.util;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class HashTableWithOpenAddressingTest {

    @Test
    public void shouldCreateEmptyHashTableWithOpenAddressing() {
        int length = 6;
        HashProbingFunction h = (key, i) -> (key + i) % length;

        HashTableWithOpenAddressing hashTableWithOpenAddressing
                = HashTableWithOpenAddressing.withLengthAndHashFunction(length, h);

        assertEquals(length, hashTableWithOpenAddressing.length);
        assertEquals(h, hashTableWithOpenAddressing.h);
    }

}

