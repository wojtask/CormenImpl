package pl.kwojtas.cormenimpl.datastructure;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ChainedHashTableTest {

    @Test
    public void shouldCreateEmptyChainedHashTable() {
        int length = 6;
        HashFunction h = key -> key % length;

        ChainedHashTable<String> chainedHashTable = ChainedHashTable.withLengthAndHashFunction(length, h);

        assertEquals(length, chainedHashTable.length);
        assertEquals(h, chainedHashTable.h);
    }

}
