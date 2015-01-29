package pl.kwojtas.cormenimpl;

import org.junit.Test;
import pl.kwojtas.cormenimpl.util.HashFunction;
import pl.kwojtas.cormenimpl.util.HashProbingFunction;
import pl.kwojtas.cormenimpl.util.List;
import pl.kwojtas.cormenimpl.util.ZeroBasedIndexedArray;

import static junit.framework.TestCase.assertNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static pl.kwojtas.cormenimpl.Chapter11.DELETED;

public class Chapter11Test {

    private ZeroBasedIndexedArray<Chapter11.ElementWithKey<String>> getExampleDirectAccessTable() {
        ZeroBasedIndexedArray<Chapter11.ElementWithKey<String>> directAccessTable = ZeroBasedIndexedArray.withLength(5);
        directAccessTable.set(0, new Chapter11.ElementWithKey<>(0, "zero"));
        directAccessTable.set(1, new Chapter11.ElementWithKey<>(1, "one"));
        directAccessTable.set(3, new Chapter11.ElementWithKey<>(3, "three"));
        return directAccessTable;
    }

    @Test
    public void shouldFindElementInDirectAccessTable() {
        // given
        ZeroBasedIndexedArray<Chapter11.ElementWithKey<String>> directAccessTable = getExampleDirectAccessTable();

        // when
        Chapter11.ElementWithKey<String> actualFoundElement = Chapter11.directAccessSearch(directAccessTable, 3);

        // then
        assertNotNull(actualFoundElement);
        assertEquals(3, actualFoundElement.key);
        assertEquals("three", actualFoundElement.data);
    }

    @Test
    public void shouldNotFindNonexistentElementInDirectAccessTable() {
        // given
        ZeroBasedIndexedArray<Chapter11.ElementWithKey<String>> directAccessTable = getExampleDirectAccessTable();

        // when
        Chapter11.ElementWithKey<String> actualFoundElement = Chapter11.directAccessSearch(directAccessTable, 2);

        // then
        assertNull(actualFoundElement);
    }

    @Test
    public void shouldInsertIntoDirectAccessTable() {
        // given
        ZeroBasedIndexedArray<Chapter11.ElementWithKey<String>> directAccessTable = getExampleDirectAccessTable();

        // when
        Chapter11.directAccessInsert(directAccessTable, new Chapter11.ElementWithKey<>(4, "four"));

        // then
        assertNotNull(directAccessTable.at(4));
        assertEquals(4, directAccessTable.at(4).key);
        assertEquals("four", directAccessTable.at(4).data);
    }

    @Test
    public void shouldDeleteFromDirectAccessTable() {
        // given
        ZeroBasedIndexedArray<Chapter11.ElementWithKey<String>> directAccessTable = getExampleDirectAccessTable();

        // when
        Chapter11.directAccessDelete(directAccessTable, directAccessTable.at(1));

        // then
        assertNull(directAccessTable.at(1));
    }

    @Test
    public void shouldFindMaximumInDirectAccessTable() {
        // given
        ZeroBasedIndexedArray<Chapter11.ElementWithKey<String>> directAccessTable = getExampleDirectAccessTable();

        // when
        Chapter11.ElementWithKey<String> actualMaximum = Chapter11.directAccessMaximum(directAccessTable);

        // then
        assertNotNull(actualMaximum);
        assertEquals(3, actualMaximum.key);
        assertEquals("three", actualMaximum.data);
    }

    @Test
    public void shouldFindElementInBitVector() {
        // given
        ZeroBasedIndexedArray<Integer> bitVector = new ZeroBasedIndexedArray<>(1,1,0,1,0);

        // when
        int actualFound = Chapter11.bitVectorSearch(bitVector, 3);

        // then
        assertEquals(1, actualFound);
    }

    @Test
    public void shouldNotFindNonexistentElementInBitVector() {
        // given
        ZeroBasedIndexedArray<Integer> bitVector = new ZeroBasedIndexedArray<>(1,1,0,1,0);

        // when
        int actualFound = Chapter11.bitVectorSearch(bitVector, 2);

        // then
        assertEquals(0, actualFound);
    }

    @Test
    public void shouldInsertIntoBitVector() {
        // given
        ZeroBasedIndexedArray<Integer> bitVector = new ZeroBasedIndexedArray<>(1,1,0,1,0);

        // when
        Chapter11.bitVectorInsert(bitVector, 4);

        // then
        assertEquals(new Integer(1), bitVector.at(4));
    }

    @Test
    public void shouldDeleteFromBitVector() {
        // given
        ZeroBasedIndexedArray<Integer> bitVector = new ZeroBasedIndexedArray<>(1,1,0,1,0);

        // when
        Chapter11.bitVectorDelete(bitVector, 1);

        // then
        assertEquals(new Integer(0), bitVector.at(1));
    }

    private ZeroBasedIndexedArray<List<Chapter11.ElementWithKey<String>>> getExampleDirectAccessTableWithNonDistinctKeys() {
        ZeroBasedIndexedArray<List<Chapter11.ElementWithKey<String>>> directAccessTable = ZeroBasedIndexedArray.withLength(5);
        directAccessTable.set(0, new List<>(new Chapter11.ElementWithKey<>(0, "zero")));
        directAccessTable.set(1, new List<>(new Chapter11.ElementWithKey<>(1, "oneA"),
                new Chapter11.ElementWithKey<>(1, "oneB"), new Chapter11.ElementWithKey<>(1, "oneC")));
        directAccessTable.set(2, new List<>());
        directAccessTable.set(3, new List<>(new Chapter11.ElementWithKey<>(3, "threeA"),
                new Chapter11.ElementWithKey<>(3, "threeB")));
        directAccessTable.set(4, new List<>());
        return directAccessTable;
    }

    @Test
    public void shouldFindElementInDirectAccessTableWithNonDistinctKeys() {
        // given
        ZeroBasedIndexedArray<List<Chapter11.ElementWithKey<String>>> directAccessTable
                = getExampleDirectAccessTableWithNonDistinctKeys();

        // when
        Chapter11.ElementWithKey<String> actualFoundElement = Chapter11.directAccessSearch_(directAccessTable, 3);

        // then
        assertNotNull(actualFoundElement);
        assertEquals(3, actualFoundElement.key);
        assertEquals("threeA", actualFoundElement.data);
    }

    @Test
    public void shouldNotFindNonexistentElementInDirectAccessTableWithNonDistinctKeys() {
        // given
        ZeroBasedIndexedArray<List<Chapter11.ElementWithKey<String>>> directAccessTable
                = getExampleDirectAccessTableWithNonDistinctKeys();

        // when
        Chapter11.ElementWithKey<String> actualFoundElement = Chapter11.directAccessSearch_(directAccessTable, 2);

        // then
        assertNull(actualFoundElement);
    }

    @Test
    public void shouldInsertIntoDirectAccessTableWithNonDistinctKeys() {
        // given
        ZeroBasedIndexedArray<List<Chapter11.ElementWithKey<String>>> directAccessTable
                = getExampleDirectAccessTableWithNonDistinctKeys();

        // when
        Chapter11.directAccessInsert_(directAccessTable, new Chapter11.ElementWithKey<>(4, "four"));

        // then
        assertNotNull(directAccessTable.at(4).head);
        assertEquals(4, directAccessTable.at(4).head.key.key);
        assertEquals("four", directAccessTable.at(4).head.key.data);
    }

    @Test
    public void shouldDeleteFromDirectAccessTableWithNonDistinctKeys() {
        // given
        ZeroBasedIndexedArray<List<Chapter11.ElementWithKey<String>>> directAccessTable
                = getExampleDirectAccessTableWithNonDistinctKeys();

        // when
        Chapter11.directAccessDelete_(directAccessTable, directAccessTable.at(1).head.next.key);

        // then
        assertEquals(2, directAccessTable.at(1).getLength());
    }

    private ZeroBasedIndexedArray<List<Chapter11.ElementWithKey<String>>> getExampleChainedHashTable() {
        ZeroBasedIndexedArray<List<Chapter11.ElementWithKey<String>>> chainedHashTable = ZeroBasedIndexedArray.withLength(5);
        chainedHashTable.set(0, new List<>(new Chapter11.ElementWithKey<>(35, "thirtyFive")));
        chainedHashTable.set(1, new List<>(new Chapter11.ElementWithKey<>(51, "fiftyOne"),
                new Chapter11.ElementWithKey<>(16, "sixteen"), new Chapter11.ElementWithKey<>(1, "one")));
        chainedHashTable.set(2, new List<>());
        chainedHashTable.set(3, new List<>(new Chapter11.ElementWithKey<>(38, "thirtyEight"),
                new Chapter11.ElementWithKey<>(23, "twentyThree")));
        chainedHashTable.set(4, new List<>());
        return chainedHashTable;
    }

    @Test
    public void shouldInsertIntoChainedHashTable() {
        // given
        ZeroBasedIndexedArray<List<Chapter11.ElementWithKey<String>>> chainedHashTable = getExampleChainedHashTable();
        HashFunction h = new HashFunction() {
            @Override
            public int compute(int key) {
                return key % chainedHashTable.length;
            }
        };

        // when
        Chapter11.chainedHashInsert(chainedHashTable, new Chapter11.ElementWithKey<>(64, "sixtyFour"), h);

        // then
        assertNotNull(chainedHashTable.at(4).head);
        assertEquals(64, chainedHashTable.at(4).head.key.key);
        assertEquals("sixtyFour", chainedHashTable.at(4).head.key.data);
    }

    @Test
    public void shouldFindElementInChainedHashTable() {
        // given
        ZeroBasedIndexedArray<List<Chapter11.ElementWithKey<String>>> chainedHashTable = getExampleChainedHashTable();
        HashFunction h = new HashFunction() {
            @Override
            public int compute(int key) {
                return key % chainedHashTable.length;
            }
        };

        // when
        Chapter11.ElementWithKey<String> actualFoundElement = Chapter11.chainedHashSearch(chainedHashTable, 23, h);

        // then
        assertNotNull(actualFoundElement);
        assertEquals(23, actualFoundElement.key);
        assertEquals("twentyThree", actualFoundElement.data);
    }

    @Test
    public void shouldNotFindNonexistentElementInChainedHashTable() {
        // given
        ZeroBasedIndexedArray<List<Chapter11.ElementWithKey<String>>> chainedHashTable = getExampleChainedHashTable();
        HashFunction h = new HashFunction() {
            @Override
            public int compute(int key) {
                return key % chainedHashTable.length;
            }
        };

        // when
        Chapter11.ElementWithKey<String> actualFoundElement = Chapter11.chainedHashSearch(chainedHashTable, 42, h);

        // then
        assertNull(actualFoundElement);
    }

    @Test
    public void shouldDeleteFromChainedHashTable() {
        // given
        ZeroBasedIndexedArray<List<Chapter11.ElementWithKey<String>>> chainedHashTable = getExampleChainedHashTable();
        HashFunction h = new HashFunction() {
            @Override
            public int compute(int key) {
                return key % chainedHashTable.length;
            }
        };

        // when
        Chapter11.chainedHashDelete(chainedHashTable, chainedHashTable.at(1).head.next.key, h);

        // then
        assertEquals(2, chainedHashTable.at(1).getLength());
    }

    @Test
    public void shouldInsertIntoHashTableWithProbing() {
        // given
        ZeroBasedIndexedArray<Integer> hashTableWithProbing = new ZeroBasedIndexedArray<>(35,51,null,38,null);
        HashProbingFunction h = new HashProbingFunction() {
            @Override
            public int compute(int key, int i) {
                int m = hashTableWithProbing.length;
                int primaryHashValue = key % m;
                return (primaryHashValue + i) % m;
            }
        };

        // when
        int actualPosition = Chapter11.hashInsert(hashTableWithProbing, 45, h);

        // then
        assertEquals(2, actualPosition);
        assertEquals(new Integer(45), hashTableWithProbing.at(2));
    }

    @Test(expected = RuntimeException.class)
    public void shouldThrowExceptionWhenInsertingIntoFullHashTableWithProbing() {
        // given
        ZeroBasedIndexedArray<Integer> hashTableWithProbing = new ZeroBasedIndexedArray<>(35,51,45,38,16);
        HashProbingFunction h = new HashProbingFunction() {
            @Override
            public int compute(int key, int i) {
                int m = hashTableWithProbing.length;
                int primaryHashValue = key % m;
                return (primaryHashValue + i) % m;
            }
        };

        // when
        Chapter11.hashInsert(hashTableWithProbing, 32, h);

        // then
    }

    @Test
    public void shouldFindElementInHashTableWithProbing() {
        // given
        ZeroBasedIndexedArray<Integer> hashTableWithProbing = new ZeroBasedIndexedArray<>(35,51,45,38,null);
        HashProbingFunction h = new HashProbingFunction() {
            @Override
            public int compute(int key, int i) {
                int m = hashTableWithProbing.length;
                int primaryHashValue = key % m;
                return (primaryHashValue + i) % m;
            }
        };

        // when
        Integer actualPosition = Chapter11.hashSearch(hashTableWithProbing, 45, h);

        // then
        assertEquals(new Integer(2), actualPosition);
    }

    @Test
    public void shouldNotFindNonexistentElementInHashTableWithProbing() {
        // given
        ZeroBasedIndexedArray<Integer> hashTableWithProbing = new ZeroBasedIndexedArray<>(35,51,45,38,null);
        HashProbingFunction h = new HashProbingFunction() {
            @Override
            public int compute(int key, int i) {
                int m = hashTableWithProbing.length;
                int primaryHashValue = key % m;
                return (primaryHashValue + i) % m;
            }
        };

        // when
        Integer actualPosition = Chapter11.hashSearch(hashTableWithProbing, 26, h);

        // then
        assertNull(actualPosition);
    }

    @Test
    public void shouldDeleteFromHashTableWithProbing() {
        // given
        ZeroBasedIndexedArray<Integer> hashTableWithProbing = new ZeroBasedIndexedArray<>(35,51,45,38,null);
        HashProbingFunction h = new HashProbingFunction() {
            @Override
            public int compute(int key, int i) {
                int m = hashTableWithProbing.length;
                int primaryHashValue = key % m;
                return (primaryHashValue + i) % m;
            }
        };

        // when
        Chapter11.hashDelete(hashTableWithProbing, 45, h);

        // then
        assertEquals(new Integer(DELETED), hashTableWithProbing.at(2));
    }

    @Test
    public void shouldInsertIntoHashTableWithProbingUsingHashInsert_() {
        // given
        ZeroBasedIndexedArray<Integer> hashTableWithProbing = new ZeroBasedIndexedArray<>(35,51,DELETED,38,DELETED);
        HashProbingFunction h = new HashProbingFunction() {
            @Override
            public int compute(int key, int i) {
                int m = hashTableWithProbing.length;
                int primaryHashValue = key % m;
                return (primaryHashValue + i) % m;
            }
        };

        // when
        int actualPosition = Chapter11.hashInsert_(hashTableWithProbing, 45, h);

        // then
        assertEquals(2, actualPosition);
        assertEquals(new Integer(45), hashTableWithProbing.at(2));
    }

    @Test(expected = RuntimeException.class)
    public void shouldThrowExceptionWhenInsertingIntoFullHashTableWithProbingUsingHashInsert_() {
        // given
        ZeroBasedIndexedArray<Integer> hashTableWithProbing = new ZeroBasedIndexedArray<>(35,51,45,38,16);
        HashProbingFunction h = new HashProbingFunction() {
            @Override
            public int compute(int key, int i) {
                int m = hashTableWithProbing.length;
                int primaryHashValue = key % m;
                return (primaryHashValue + i) % m;
            }
        };

        // when
        Chapter11.hashInsert_(hashTableWithProbing, 32, h);

        // then
    }

}
