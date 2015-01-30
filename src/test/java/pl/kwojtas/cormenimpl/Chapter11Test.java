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

    private ZeroBasedIndexedArray<Chapter11.ElementWithKey<String>> getExampleDirectAddressTable() {
        ZeroBasedIndexedArray<Chapter11.ElementWithKey<String>> directAddressTable = ZeroBasedIndexedArray.withLength(5);
        directAddressTable.set(0, new Chapter11.ElementWithKey<>(0, "zero"));
        directAddressTable.set(1, new Chapter11.ElementWithKey<>(1, "one"));
        directAddressTable.set(3, new Chapter11.ElementWithKey<>(3, "three"));
        return directAddressTable;
    }

    @Test
    public void shouldFindElementInDirectAddressTable() {
        // given
        ZeroBasedIndexedArray<Chapter11.ElementWithKey<String>> directAddressTable = getExampleDirectAddressTable();

        // when
        Chapter11.ElementWithKey<String> actualFoundElement = Chapter11.directAddressSearch(directAddressTable, 3);

        // then
        assertNotNull(actualFoundElement);
        assertEquals(3, actualFoundElement.key);
        assertEquals("three", actualFoundElement.data);
    }

    @Test
    public void shouldNotFindNonexistentElementInDirectAddressTable() {
        // given
        ZeroBasedIndexedArray<Chapter11.ElementWithKey<String>> directAddressTable = getExampleDirectAddressTable();

        // when
        Chapter11.ElementWithKey<String> actualFoundElement = Chapter11.directAddressSearch(directAddressTable, 2);

        // then
        assertNull(actualFoundElement);
    }

    @Test
    public void shouldInsertIntoDirectAddressTable() {
        // given
        ZeroBasedIndexedArray<Chapter11.ElementWithKey<String>> directAddressTable = getExampleDirectAddressTable();

        // when
        Chapter11.directAddressInsert(directAddressTable, new Chapter11.ElementWithKey<>(4, "four"));

        // then
        assertNotNull(directAddressTable.at(4));
        assertEquals(4, directAddressTable.at(4).key);
        assertEquals("four", directAddressTable.at(4).data);
    }

    @Test
    public void shouldDeleteFromDirectAddressTable() {
        // given
        ZeroBasedIndexedArray<Chapter11.ElementWithKey<String>> directAddressTable = getExampleDirectAddressTable();

        // when
        Chapter11.directAddressDelete(directAddressTable, directAddressTable.at(1));

        // then
        assertNull(directAddressTable.at(1));
    }

    @Test
    public void shouldFindMaximumInDirectAddressTable() {
        // given
        ZeroBasedIndexedArray<Chapter11.ElementWithKey<String>> directAddressTable = getExampleDirectAddressTable();

        // when
        Chapter11.ElementWithKey<String> actualMaximum = Chapter11.directAddressMaximum(directAddressTable);

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

    private ZeroBasedIndexedArray<List<Chapter11.ElementWithKey<String>>> getExampleDirectAddressTableWithNonDistinctKeys() {
        ZeroBasedIndexedArray<List<Chapter11.ElementWithKey<String>>> directAddressTable = ZeroBasedIndexedArray.withLength(5);
        directAddressTable.set(0, new List<>(new Chapter11.ElementWithKey<>(0, "zero")));
        directAddressTable.set(1, new List<>(new Chapter11.ElementWithKey<>(1, "oneA"),
                new Chapter11.ElementWithKey<>(1, "oneB"), new Chapter11.ElementWithKey<>(1, "oneC")));
        directAddressTable.set(2, new List<>());
        directAddressTable.set(3, new List<>(new Chapter11.ElementWithKey<>(3, "threeA"),
                new Chapter11.ElementWithKey<>(3, "threeB")));
        directAddressTable.set(4, new List<>());
        return directAddressTable;
    }

    @Test
    public void shouldFindElementInDirectAddressTableWithNonDistinctKeys() {
        // given
        ZeroBasedIndexedArray<List<Chapter11.ElementWithKey<String>>> directAddressTable
                = getExampleDirectAddressTableWithNonDistinctKeys();

        // when
        Chapter11.ElementWithKey<String> actualFoundElement = Chapter11.directAddressSearch_(directAddressTable, 3);

        // then
        assertNotNull(actualFoundElement);
        assertEquals(3, actualFoundElement.key);
        assertEquals("threeA", actualFoundElement.data);
    }

    @Test
    public void shouldNotFindNonexistentElementInDirectAddressTableWithNonDistinctKeys() {
        // given
        ZeroBasedIndexedArray<List<Chapter11.ElementWithKey<String>>> directAddressTable
                = getExampleDirectAddressTableWithNonDistinctKeys();

        // when
        Chapter11.ElementWithKey<String> actualFoundElement = Chapter11.directAddressSearch_(directAddressTable, 2);

        // then
        assertNull(actualFoundElement);
    }

    @Test
    public void shouldInsertIntoDirectAddressTableWithNonDistinctKeys() {
        // given
        ZeroBasedIndexedArray<List<Chapter11.ElementWithKey<String>>> directAddressTable
                = getExampleDirectAddressTableWithNonDistinctKeys();

        // when
        Chapter11.directAddressInsert_(directAddressTable, new Chapter11.ElementWithKey<>(4, "four"));

        // then
        assertNotNull(directAddressTable.at(4).head);
        assertEquals(4, directAddressTable.at(4).head.key.key);
        assertEquals("four", directAddressTable.at(4).head.key.data);
    }

    @Test
    public void shouldDeleteFromDirectAddressTableWithNonDistinctKeys() {
        // given
        ZeroBasedIndexedArray<List<Chapter11.ElementWithKey<String>>> directAddressTable
                = getExampleDirectAddressTableWithNonDistinctKeys();

        // when
        Chapter11.directAddressDelete_(directAddressTable, directAddressTable.at(1).head.next.key);

        // then
        assertEquals(2, directAddressTable.at(1).getLength());
    }

    private Chapter11.HugeArray<String> getExampleHugeArray() {
        Chapter11.HugeArray<String> hugeArray = new Chapter11.HugeArray<>(100, 5);
        hugeArray.T.set(56, 1);
        hugeArray.T.set(23, 2);
        hugeArray.T.set(10, 3);
        hugeArray.S.set(1, new Chapter11.ElementWithKey<>(56, "fiftySix"));
        hugeArray.S.set(2, new Chapter11.ElementWithKey<>(23, "twentyThree"));
        hugeArray.S.set(3, new Chapter11.ElementWithKey<>(10, "ten"));
        hugeArray.S.top = 3;
        return hugeArray;
    }

    @Test
    public void shouldFindElementInHugeArray() {
        // given
        Chapter11.HugeArray<String> hugeArray = getExampleHugeArray();

        // when
        Chapter11.ElementWithKey<String> actualFoundElement = Chapter11.hugeArraySearch(hugeArray, 23);

        // then
        assertNotNull(actualFoundElement);
        assertEquals(23, actualFoundElement.key);
        assertEquals("twentyThree", actualFoundElement.data);
    }

    @Test
    public void shouldNotFindNonexistentElementInHugeArray() {
        // given
        Chapter11.HugeArray<String> hugeArray = getExampleHugeArray();

        // when
        Chapter11.ElementWithKey<String> actualFoundElement = Chapter11.hugeArraySearch(hugeArray, 66);

        // then
        assertNull(actualFoundElement);
    }

    @Test
    public void shouldInsertIntoHugeArray() {
        // given
        Chapter11.HugeArray<String> hugeArray = getExampleHugeArray();

        // when
        Chapter11.hugeArrayInsert(hugeArray, new Chapter11.ElementWithKey<>(66, "sixtySix"));

        // then
        assertEquals(4, hugeArray.S.top);
        assertEquals(66, hugeArray.S.at(4).key);
        assertEquals("sixtySix", hugeArray.S.at(4).data);
        assertEquals(new Integer(4), hugeArray.T.at(66));
    }

    @Test
    public void shouldDeleteFromHugeArray() {
        // given
        Chapter11.HugeArray<String> hugeArray = getExampleHugeArray();

        // when
        Chapter11.hugeArrayDelete(hugeArray, new Chapter11.ElementWithKey<>(56, "fiftySix"));

        // then
        assertEquals(2, hugeArray.S.top);
        assertEquals(10, hugeArray.S.at(1).key);
        assertEquals("ten", hugeArray.S.at(1).data);
        assertEquals(new Integer(1), hugeArray.T.at(10));
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
