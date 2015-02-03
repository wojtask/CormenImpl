package pl.kwojtas.cormenimpl;

import org.junit.Test;
import pl.kwojtas.cormenimpl.Chapter11.ElementWithKey;
import pl.kwojtas.cormenimpl.Chapter11.HugeArray;
import pl.kwojtas.cormenimpl.util.HashFunction;
import pl.kwojtas.cormenimpl.util.HashProbingFunction;
import pl.kwojtas.cormenimpl.util.List;
import pl.kwojtas.cormenimpl.util.ZeroBasedIndexedArray;

import static junit.framework.TestCase.assertNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static pl.kwojtas.cormenimpl.Chapter11.DELETED;

public class Chapter11Test {

    private ZeroBasedIndexedArray<ElementWithKey<String>> getExemplaryDirectAddressTable() {
        ZeroBasedIndexedArray<ElementWithKey<String>> directAddressTable = ZeroBasedIndexedArray.withLength(5);
        directAddressTable.set(0, new ElementWithKey<>(0, "zero"));
        directAddressTable.set(1, new ElementWithKey<>(1, "one"));
        directAddressTable.set(3, new ElementWithKey<>(3, "three"));
        return directAddressTable;
    }

    @Test
    public void shouldFindElementInDirectAddressTable() {
        // given
        ZeroBasedIndexedArray<ElementWithKey<String>> directAddressTable = getExemplaryDirectAddressTable();
        ElementWithKey<String> element = new ElementWithKey<>(3, "three");

        // when
        ElementWithKey<String> actualFoundElement = Chapter11.directAddressSearch(directAddressTable, element.key);

        // then
        assertEquals(element.key, actualFoundElement.key);
        assertEquals(element.data, actualFoundElement.data);
    }

    @Test
    public void shouldNotFindNonexistentElementInDirectAddressTable() {
        // given
        ZeroBasedIndexedArray<ElementWithKey<String>> directAddressTable = getExemplaryDirectAddressTable();
        int key = 2;

        // when
        ElementWithKey<String> actualFoundElement = Chapter11.directAddressSearch(directAddressTable, key);

        // then
        assertNull(actualFoundElement);
    }

    @Test
    public void shouldInsertIntoDirectAddressTable() {
        // given
        ZeroBasedIndexedArray<ElementWithKey<String>> directAddressTable = getExemplaryDirectAddressTable();
        ElementWithKey<String> element = new ElementWithKey<>(4, "four");
        int key = 4;

        // when
        Chapter11.directAddressInsert(directAddressTable, element);

        // then
        assertEquals(element.key, directAddressTable.at(key).key);
        assertEquals(element.data, directAddressTable.at(key).data);
    }

    @Test
    public void shouldDeleteFromDirectAddressTable() {
        // given
        ZeroBasedIndexedArray<ElementWithKey<String>> directAddressTable = getExemplaryDirectAddressTable();

        // when
        Chapter11.directAddressDelete(directAddressTable, directAddressTable.at(1));

        // then
        assertNull(directAddressTable.at(1));
    }

    @Test
    public void shouldFindMaximumInDirectAddressTable() {
        // given
        ZeroBasedIndexedArray<ElementWithKey<String>> directAddressTable = getExemplaryDirectAddressTable();
        ElementWithKey<String> expectedMaximum = new ElementWithKey<>(3, "three");

        // when
        ElementWithKey<String> actualMaximum = Chapter11.directAddressMaximum(directAddressTable);

        // then
        assertEquals(expectedMaximum.key, actualMaximum.key);
        assertEquals(expectedMaximum.data, actualMaximum.data);
    }

    @Test
    public void shouldFindElementInBitVector() {
        // given
        ZeroBasedIndexedArray<Integer> bitVector = new ZeroBasedIndexedArray<>(1,1,0,1,0);
        int key = 3;

        // when
        int actualFound = Chapter11.bitVectorSearch(bitVector, key);

        // then
        assertEquals(1, actualFound);
    }

    @Test
    public void shouldNotFindNonexistentElementInBitVector() {
        // given
        ZeroBasedIndexedArray<Integer> bitVector = new ZeroBasedIndexedArray<>(1,1,0,1,0);
        int key = 2;

        // when
        int actualFound = Chapter11.bitVectorSearch(bitVector, key);

        // then
        assertEquals(0, actualFound);
    }

    @Test
    public void shouldInsertIntoBitVector() {
        // given
        ZeroBasedIndexedArray<Integer> bitVector = new ZeroBasedIndexedArray<>(1,1,0,1,0);
        int key = 4;

        // when
        Chapter11.bitVectorInsert(bitVector, key);

        // then
        assertEquals(new Integer(1), bitVector.at(key));
    }

    @Test
    public void shouldDeleteFromBitVector() {
        // given
        ZeroBasedIndexedArray<Integer> bitVector = new ZeroBasedIndexedArray<>(1,1,0,1,0);
        int key = 1;

        // when
        Chapter11.bitVectorDelete(bitVector, key);

        // then
        assertEquals(new Integer(0), bitVector.at(key));
    }

    private ZeroBasedIndexedArray<List<ElementWithKey<String>>> getExemplaryDirectAddressTableWithNonDistinctKeys() {
        ZeroBasedIndexedArray<List<ElementWithKey<String>>> directAddressTable = ZeroBasedIndexedArray.withLength(5);
        directAddressTable.set(0, new List<>(new ElementWithKey<>(0, "zero")));
        directAddressTable.set(1, new List<>(new ElementWithKey<>(1, "oneA"), new ElementWithKey<>(1, "oneB"),
                new ElementWithKey<>(1, "oneC")));
        directAddressTable.set(2, new List<>());
        directAddressTable.set(3, new List<>(new ElementWithKey<>(3, "threeA"), new ElementWithKey<>(3, "threeB")));
        directAddressTable.set(4, new List<>());
        return directAddressTable;
    }

    @Test
    public void shouldFindElementInDirectAddressTableWithNonDistinctKeys() {
        // given
        ZeroBasedIndexedArray<List<ElementWithKey<String>>> directAddressTable
                = getExemplaryDirectAddressTableWithNonDistinctKeys();
        ElementWithKey<String> element = new ElementWithKey<>(3, "threeA");

        // when
        ElementWithKey<String> actualFoundElement = Chapter11.directAddressSearch_(directAddressTable, element.key);

        // then
        assertEquals(element.key, actualFoundElement.key);
        assertEquals(element.data, actualFoundElement.data);
    }

    @Test
    public void shouldNotFindNonexistentElementInDirectAddressTableWithNonDistinctKeys() {
        // given
        ZeroBasedIndexedArray<List<ElementWithKey<String>>> directAddressTable
                = getExemplaryDirectAddressTableWithNonDistinctKeys();
        int key = 2;

        // when
        ElementWithKey<String> actualFoundElement = Chapter11.directAddressSearch_(directAddressTable, key);

        // then
        assertNull(actualFoundElement);
    }

    @Test
    public void shouldInsertIntoDirectAddressTableWithNonDistinctKeys() {
        // given
        ZeroBasedIndexedArray<List<ElementWithKey<String>>> directAddressTable
                = getExemplaryDirectAddressTableWithNonDistinctKeys();
        ElementWithKey<String> element = new ElementWithKey<>(4, "four");
        int key = 4;

        // when
        Chapter11.directAddressInsert_(directAddressTable, element);

        // then
        assertEquals(element.key, directAddressTable.at(key).head.key.key);
        assertEquals(element.data, directAddressTable.at(key).head.key.data);
    }

    @Test
    public void shouldDeleteFromDirectAddressTableWithNonDistinctKeys() {
        // given
        ZeroBasedIndexedArray<List<ElementWithKey<String>>> directAddressTable
                = getExemplaryDirectAddressTableWithNonDistinctKeys();
        int key = 1;

        // when
        Chapter11.directAddressDelete_(directAddressTable, directAddressTable.at(key).head.next.key);

        // then
        assertEquals(2, directAddressTable.at(key).getLength());
    }

    private HugeArray<String> getExemplaryHugeArray() {
        HugeArray<String> hugeArray = new HugeArray<>(100, 5);
        hugeArray.T.set(56, 1);
        hugeArray.T.set(23, 2);
        hugeArray.T.set(10, 3);
        hugeArray.S.set(1, new ElementWithKey<>(56, "fiftySix"));
        hugeArray.S.set(2, new ElementWithKey<>(23, "twentyThree"));
        hugeArray.S.set(3, new ElementWithKey<>(10, "ten"));
        hugeArray.S.top = 3;
        return hugeArray;
    }

    @Test
    public void shouldFindElementInHugeArray() {
        // given
        HugeArray<String> hugeArray = getExemplaryHugeArray();
        ElementWithKey<String> element = new ElementWithKey<>(23, "twentyThree");

        // when
        ElementWithKey<String> actualFoundElement = Chapter11.hugeArraySearch(hugeArray, element.key);

        // then
        assertEquals(element.key, actualFoundElement.key);
        assertEquals(element.data, actualFoundElement.data);
    }

    @Test
    public void shouldNotFindNonexistentElementInHugeArray() {
        // given
        HugeArray<String> hugeArray = getExemplaryHugeArray();
        int key = 66;

        // when
        ElementWithKey<String> actualFoundElement = Chapter11.hugeArraySearch(hugeArray, key);

        // then
        assertNull(actualFoundElement);
    }

    @Test
    public void shouldInsertIntoHugeArray() {
        // given
        HugeArray<String> hugeArray = getExemplaryHugeArray();
        ElementWithKey<String> element = new ElementWithKey<>(66, "sixtySix");
        int hugeArraySize = hugeArray.S.top;

        // when
        Chapter11.hugeArrayInsert(hugeArray, element);

        // then
        assertEquals(hugeArraySize + 1, hugeArray.S.top);
        assertEquals(element.key, hugeArray.S.at(hugeArraySize + 1).key);
        assertEquals(element.data, hugeArray.S.at(hugeArraySize + 1).data);
        assertEquals(new Integer(hugeArraySize + 1), hugeArray.T.at(element.key));
    }

    @Test
    public void shouldDeleteFromHugeArray() {
        // given
        HugeArray<String> hugeArray = getExemplaryHugeArray();
        ElementWithKey<String> element = new ElementWithKey<>(56, "fiftySix");
        int hugeArraySize = hugeArray.S.top;

        // when
        Chapter11.hugeArrayDelete(hugeArray, element);

        // then
        assertEquals(hugeArraySize - 1, hugeArray.S.top);
        assertEquals(10, hugeArray.S.at(1).key);
        assertEquals("ten", hugeArray.S.at(1).data);
        assertEquals(new Integer(1), hugeArray.T.at(10));
    }

    private ZeroBasedIndexedArray<List<ElementWithKey<String>>> getExemplaryChainedHashTable() {
        ZeroBasedIndexedArray<List<ElementWithKey<String>>> chainedHashTable = ZeroBasedIndexedArray.withLength(5);
        chainedHashTable.set(0, new List<>(new ElementWithKey<>(35, "thirtyFive")));
        chainedHashTable.set(1, new List<>(new ElementWithKey<>(51, "fiftyOne"), new ElementWithKey<>(16, "sixteen"),
                new ElementWithKey<>(1, "one")));
        chainedHashTable.set(2, new List<>());
        chainedHashTable.set(3, new List<>(new ElementWithKey<>(38, "thirtyEight"), new ElementWithKey<>(23, "twentyThree")));
        chainedHashTable.set(4, new List<>());
        return chainedHashTable;
    }

    @Test
    public void shouldInsertIntoChainedHashTable() {
        // given
        ZeroBasedIndexedArray<List<ElementWithKey<String>>> chainedHashTable = getExemplaryChainedHashTable();
        HashFunction h = new HashFunction() {
            @Override
            public int compute(int key) {
                return key % chainedHashTable.length;
            }
        };
        ElementWithKey<String> element = new ElementWithKey<>(64, "sixtyFour");
        int hashedKey = h.compute(element.key);

        // when
        Chapter11.chainedHashInsert(chainedHashTable, element, h);

        // then
        assertEquals(element.key, chainedHashTable.at(hashedKey).head.key.key);
        assertEquals(element.data, chainedHashTable.at(hashedKey).head.key.data);
    }

    @Test
    public void shouldFindElementInChainedHashTable() {
        // given
        ZeroBasedIndexedArray<List<ElementWithKey<String>>> chainedHashTable = getExemplaryChainedHashTable();
        HashFunction h = new HashFunction() {
            @Override
            public int compute(int key) {
                return key % chainedHashTable.length;
            }
        };
        ElementWithKey<String> element = new ElementWithKey<>(23, "twentyThree");

        // when
        ElementWithKey<String> actualFoundElement = Chapter11.chainedHashSearch(chainedHashTable, element.key, h);

        // then
        assertEquals(element.key, actualFoundElement.key);
        assertEquals(element.data, actualFoundElement.data);
    }

    @Test
    public void shouldNotFindNonexistentElementInChainedHashTable() {
        // given
        ZeroBasedIndexedArray<List<ElementWithKey<String>>> chainedHashTable = getExemplaryChainedHashTable();
        HashFunction h = new HashFunction() {
            @Override
            public int compute(int key) {
                return key % chainedHashTable.length;
            }
        };
        int key = 42;

        // when
        ElementWithKey<String> actualFoundElement = Chapter11.chainedHashSearch(chainedHashTable, key, h);

        // then
        assertNull(actualFoundElement);
    }

    @Test
    public void shouldDeleteFromChainedHashTable() {
        // given
        ZeroBasedIndexedArray<List<ElementWithKey<String>>> chainedHashTable = getExemplaryChainedHashTable();
        HashFunction h = new HashFunction() {
            @Override
            public int compute(int key) {
                return key % chainedHashTable.length;
            }
        };
        ElementWithKey<String> element = chainedHashTable.at(1).head.next.key;
        int chainLength = chainedHashTable.at(1).getLength();

        // when
        Chapter11.chainedHashDelete(chainedHashTable, element, h);

        // then
        assertEquals(chainLength - 1, chainedHashTable.at(1).getLength());
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
        int key = 45;

        // when
        int actualPosition = Chapter11.hashInsert(hashTableWithProbing, key, h);

        // then
        assertEquals(2, actualPosition);
        assertEquals(new Integer(key), hashTableWithProbing.at(2));
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
        int key = 32;

        try {
            // when
            Chapter11.hashInsert(hashTableWithProbing, key, h);
        } catch (RuntimeException e) {
            // then
            assertEquals("overflow", e.getMessage());
            throw e;
        }
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
        int key = 45;

        // when
        Integer actualPosition = Chapter11.hashSearch(hashTableWithProbing, key, h);

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
        int key = 26;

        // when
        Integer actualPosition = Chapter11.hashSearch(hashTableWithProbing, key, h);

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
        int key = 45;

        // when
        Chapter11.hashDelete(hashTableWithProbing, key, h);

        // then
        assertEquals(DELETED, hashTableWithProbing.at(2));
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
        int key = 45;

        // when
        int actualPosition = Chapter11.hashInsert_(hashTableWithProbing, key, h);

        // then
        assertEquals(2, actualPosition);
        assertEquals(new Integer(key), hashTableWithProbing.at(2));
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
        int key = 32;

        try {
            // when
            Chapter11.hashInsert_(hashTableWithProbing, key, h);
        } catch (RuntimeException e) {
            // then
            assertEquals("overflow", e.getMessage());
            throw e;
        }
    }

    @Test
    public void shouldFindElementUsingQuadraticProbing() {
        // given
        ZeroBasedIndexedArray<Integer> hashTable = new ZeroBasedIndexedArray<>(24,null,34,35,51,null,27,null);
        HashFunction h = new HashFunction() {
            @Override
            public int compute(int key) {
                return key % hashTable.length;
            }
        };
        int key = 27;

        // when
        Integer actualPosition = Chapter11.quadraticProbingSearch(hashTable, key, h);

        // then
        assertEquals(new Integer(6), actualPosition);
    }

    @Test
    public void shouldNotFindNonexistentElementUsingQuadraticProbing() {
        // given
        ZeroBasedIndexedArray<Integer> hashTable = new ZeroBasedIndexedArray<>(24,null,34,35,51,null,27,null);
        HashFunction h = new HashFunction() {
            @Override
            public int compute(int key) {
                return key % hashTable.length;
            }
        };
        int key = 43;

        // when
        Integer actualPosition = Chapter11.quadraticProbingSearch(hashTable, key, h);

        // then
        assertNull(actualPosition);
    }

}
