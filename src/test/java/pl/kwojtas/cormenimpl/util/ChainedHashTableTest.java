package pl.kwojtas.cormenimpl.util;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ChainedHashTableTest {

    @Test
    public void shouldCreateEmptyChainedHashTable() {
        int length = 6;
        HashFunction h = new HashFunction() {
            @Override
            public int compute(int key) {
                return key % length;
            }
        };

        ChainedHashTable<String> chainedHashTable = ChainedHashTable.withLengthAndHashFunction(length, h);

        assertEquals(length, chainedHashTable.length);
        assertEquals(h, chainedHashTable.h);
    }

}
