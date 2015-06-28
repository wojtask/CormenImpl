package pl.kwojtas.cormenimpl.util;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ChainedHashTableTest {

    @Test
    public void shouldCreateEmptyChainedHashTable() {
        // given
        int length = 6;
        HashFunction h = new HashFunction() {
            @Override
            public int compute(int key) {
                return key % length;
            }
        };

        // when
        ChainedHashTable<String> chainedHashTable = ChainedHashTable.withLengthAndHashFunction(length, h);

        // then
        assertEquals(length, chainedHashTable.length);
        assertEquals(h, chainedHashTable.h);
    }

}
