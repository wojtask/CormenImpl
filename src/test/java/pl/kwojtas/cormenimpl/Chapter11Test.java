package pl.kwojtas.cormenimpl;

import org.junit.Test;
import pl.kwojtas.cormenimpl.Chapter11.HugeArray;
import pl.kwojtas.cormenimpl.util.ChainedHashTable;
import pl.kwojtas.cormenimpl.util.Element;
import pl.kwojtas.cormenimpl.util.HashFunction;
import pl.kwojtas.cormenimpl.util.HashProbingFunction;
import pl.kwojtas.cormenimpl.util.HashTableWithFreeList;
import pl.kwojtas.cormenimpl.util.HashTableWithProbing;
import pl.kwojtas.cormenimpl.util.List;
import pl.kwojtas.cormenimpl.util.ZeroBasedIndexedArray;

import static junit.framework.TestCase.assertNull;
import static org.junit.Assert.assertEquals;
import static pl.kwojtas.cormenimpl.Chapter11.DELETED;

public class Chapter11Test {

    private ZeroBasedIndexedArray<Element<String>> getExemplaryDirectAddressTable() {
        ZeroBasedIndexedArray<Element<String>> directAddressTable = ZeroBasedIndexedArray.withLength(5);
        directAddressTable.set(0, new Element<>(0, "zero"));
        directAddressTable.set(1, new Element<>(1, "one"));
        directAddressTable.set(3, new Element<>(3, "three"));
        return directAddressTable;
    }

    @Test
    public void shouldFindElementInDirectAddressTable() {
        // given
        ZeroBasedIndexedArray<Element<String>> directAddressTable = getExemplaryDirectAddressTable();
        Element<String> element = new Element<>(3, "three");

        // when
        Element<String> actualFoundElement = Chapter11.directAddressSearch(directAddressTable, element.key);

        // then
        assertEquals(element.key, actualFoundElement.key);
        assertEquals(element.data, actualFoundElement.data);
    }

    @Test
    public void shouldNotFindNonexistentElementInDirectAddressTable() {
        // given
        ZeroBasedIndexedArray<Element<String>> directAddressTable = getExemplaryDirectAddressTable();
        int key = 2;

        // when
        Element<String> actualFoundElement = Chapter11.directAddressSearch(directAddressTable, key);

        // then
        assertNull(actualFoundElement);
    }

    @Test
    public void shouldInsertIntoDirectAddressTable() {
        // given
        ZeroBasedIndexedArray<Element<String>> directAddressTable = getExemplaryDirectAddressTable();
        Element<String> element = new Element<>(4, "four");
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
        ZeroBasedIndexedArray<Element<String>> directAddressTable = getExemplaryDirectAddressTable();

        // when
        Chapter11.directAddressDelete(directAddressTable, directAddressTable.at(1));

        // then
        assertNull(directAddressTable.at(1));
    }

    @Test
    public void shouldFindMaximumInDirectAddressTable() {
        // given
        ZeroBasedIndexedArray<Element<String>> directAddressTable = getExemplaryDirectAddressTable();
        Element<String> expectedMaximum = new Element<>(3, "three");

        // when
        Element<String> actualMaximum = Chapter11.directAddressMaximum(directAddressTable);

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

    private ZeroBasedIndexedArray<List<Element<String>>> getExemplaryDirectAddressTableWithNonDistinctKeys() {
        ZeroBasedIndexedArray<List<Element<String>>> directAddressTable = ZeroBasedIndexedArray.withLength(5);
        directAddressTable.set(0, new List<>(new Element<>(0, "zero")));
        directAddressTable.set(1, new List<>(new Element<>(1, "oneA"), new Element<>(1, "oneB"),
                new Element<>(1, "oneC")));
        directAddressTable.set(2, new List<>());
        directAddressTable.set(3, new List<>(new Element<>(3, "threeA"), new Element<>(3, "threeB")));
        directAddressTable.set(4, new List<>());
        return directAddressTable;
    }

    @Test
    public void shouldFindElementInDirectAddressTableWithNonDistinctKeys() {
        // given
        ZeroBasedIndexedArray<List<Element<String>>> directAddressTable
                = getExemplaryDirectAddressTableWithNonDistinctKeys();
        Element<String> element = new Element<>(3, "threeA");

        // when
        Element<String> actualFoundElement = Chapter11.directAddressSearch_(directAddressTable, element.key);

        // then
        assertEquals(element.key, actualFoundElement.key);
        assertEquals(element.data, actualFoundElement.data);
    }

    @Test
    public void shouldNotFindNonexistentElementInDirectAddressTableWithNonDistinctKeys() {
        // given
        ZeroBasedIndexedArray<List<Element<String>>> directAddressTable
                = getExemplaryDirectAddressTableWithNonDistinctKeys();
        int key = 2;

        // when
        Element<String> actualFoundElement = Chapter11.directAddressSearch_(directAddressTable, key);

        // then
        assertNull(actualFoundElement);
    }

    @Test
    public void shouldInsertIntoDirectAddressTableWithNonDistinctKeys() {
        // given
        ZeroBasedIndexedArray<List<Element<String>>> directAddressTable
                = getExemplaryDirectAddressTableWithNonDistinctKeys();
        Element<String> element = new Element<>(4, "four");
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
        ZeroBasedIndexedArray<List<Element<String>>> directAddressTable
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
        hugeArray.S.set(1, new Element<>(56, "fiftySix"));
        hugeArray.S.set(2, new Element<>(23, "twentyThree"));
        hugeArray.S.set(3, new Element<>(10, "ten"));
        hugeArray.S.top = 3;
        return hugeArray;
    }

    @Test
    public void shouldFindElementInHugeArray() {
        // given
        HugeArray<String> hugeArray = getExemplaryHugeArray();
        Element<String> element = new Element<>(23, "twentyThree");

        // when
        Element<String> actualFoundElement = Chapter11.hugeArraySearch(hugeArray, element.key);

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
        Element<String> actualFoundElement = Chapter11.hugeArraySearch(hugeArray, key);

        // then
        assertNull(actualFoundElement);
    }

    @Test
    public void shouldInsertIntoHugeArray() {
        // given
        HugeArray<String> hugeArray = getExemplaryHugeArray();
        Element<String> element = new Element<>(66, "sixtySix");
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
        Element<String> element = new Element<>(56, "fiftySix");
        int hugeArraySize = hugeArray.S.top;

        // when
        Chapter11.hugeArrayDelete(hugeArray, element);

        // then
        assertEquals(hugeArraySize - 1, hugeArray.S.top);
        assertEquals(10, hugeArray.S.at(1).key);
        assertEquals("ten", hugeArray.S.at(1).data);
        assertEquals(new Integer(1), hugeArray.T.at(10));
    }

    private ChainedHashTable<String> getExemplaryChainedHashTable() {
        ChainedHashTable<String> chainedHashTable = ChainedHashTable.withLengthAndHashFunction(5,
                new HashFunction() {
                    @Override
                    public int compute(int key) {
                        return key % 5;
                    }
                }
        );
        chainedHashTable.set(0, new List<>(new Element<>(35, "thirtyFive")));
        chainedHashTable.set(1, new List<>(new Element<>(51, "fiftyOne"), new Element<>(16, "sixteen"), new Element<>(1, "one")));
        chainedHashTable.set(3, new List<>(new Element<>(38, "thirtyEight"), new Element<>(23, "twentyThree")));
        return chainedHashTable;
    }

    @Test
    public void shouldInsertIntoChainedHashTable() {
        // given
        ChainedHashTable<String> chainedHashTable = getExemplaryChainedHashTable();
        Element<String> element = new Element<>(64, "sixtyFour");
        int hashedKey = chainedHashTable.h.compute(element.key);

        // when
        Chapter11.chainedHashInsert(chainedHashTable, element);

        // then
        assertEquals(element.key, chainedHashTable.at(hashedKey).head.key.key);
        assertEquals(element.data, chainedHashTable.at(hashedKey).head.key.data);
    }

    @Test
    public void shouldFindElementInChainedHashTable() {
        // given
        ChainedHashTable<String> chainedHashTable = getExemplaryChainedHashTable();
        Element<String> element = new Element<>(23, "twentyThree");

        // when
        Element<String> actualFoundElement = Chapter11.chainedHashSearch(chainedHashTable, element.key);

        // then
        assertEquals(element.key, actualFoundElement.key);
        assertEquals(element.data, actualFoundElement.data);
    }

    @Test
    public void shouldNotFindNonexistentElementInChainedHashTable() {
        // given
        ChainedHashTable<String> chainedHashTable = getExemplaryChainedHashTable();
        int key = 42;

        // when
        Element<String> actualFoundElement = Chapter11.chainedHashSearch(chainedHashTable, key);

        // then
        assertNull(actualFoundElement);
    }

    @Test
    public void shouldDeleteFromChainedHashTable() {
        // given
        ChainedHashTable<String> chainedHashTable = getExemplaryChainedHashTable();
        Element<String> element = chainedHashTable.at(1).head.next.key;
        int chainLength = chainedHashTable.at(1).getLength();

        // when
        Chapter11.chainedHashDelete(chainedHashTable, element);

        // then
        assertEquals(chainLength - 1, chainedHashTable.at(1).getLength());
    }

    @Test
    public void shouldInsertOntoFreePositionInHashTableWithFreeList() {
        // given
        HashTableWithFreeList<String> hashTableWithFreeList = getExemplaryHashTableWithFreeList();
        Element<String> element = new Element<>(43, "fortyThree");
        int expectedPosition = 3;
        int expectedFreeListHeadPosition = 0;

        // when
        int actualPosition = Chapter11.inPlaceChainedHashInsert(hashTableWithFreeList, element);

        // then
        assertEquals(expectedPosition, actualPosition);
        assertEquals(element, hashTableWithFreeList.at(expectedPosition).element);
        assertNull(hashTableWithFreeList.at(expectedPosition).prev);
        assertNull(hashTableWithFreeList.at(expectedPosition).next);
        assertEquals(new Integer(expectedFreeListHeadPosition), hashTableWithFreeList.free);
    }

    private HashTableWithFreeList<String> getExemplaryHashTableWithFreeList() {
        HashTableWithFreeList<String> hashTableWithFreeList = HashTableWithFreeList.withLengthAndHashFunction(8,
                new HashFunction() {
                    @Override
                    public int compute(int key) {
                        return key % 8;
                    }
                }
        );
        hashTableWithFreeList.free = 3;
        hashTableWithFreeList.at(0).prev = 3;
        hashTableWithFreeList.at(1).element = new Element<>(17, "seventeen");
        hashTableWithFreeList.at(1).next = 5;
        hashTableWithFreeList.at(2).element = new Element<>(23, "twentyThree");
        hashTableWithFreeList.at(2).prev = 7;
        hashTableWithFreeList.at(3).next = 0;
        hashTableWithFreeList.at(4).element = new Element<>(1, "one");
        hashTableWithFreeList.at(4).prev = 5;
        hashTableWithFreeList.at(5).element = new Element<>(9, "nine");
        hashTableWithFreeList.at(5).next = 4;
        hashTableWithFreeList.at(5).prev = 1;
        hashTableWithFreeList.at(6).element = new Element<>(6, "six");
        hashTableWithFreeList.at(7).element = new Element<>(15, "fifteen");
        hashTableWithFreeList.at(7).next = 2;
        return hashTableWithFreeList;
    }

    @Test
    public void shouldInsertOntoTakenPositionWithEligibleElementInHashTableWithFreeList() {
        // given
        HashTableWithFreeList<String> hashTableWithFreeList = getExemplaryHashTableWithFreeList();
        Element<String> element = new Element<>(25, "twentyFive");
        int expectedPosition = 3;
        int expectedFreeListHeadPosition = 0;

        // when
        int actualPosition = Chapter11.inPlaceChainedHashInsert(hashTableWithFreeList, element);

        // then
        assertEquals(expectedPosition, actualPosition);
        assertEquals(element, hashTableWithFreeList.at(expectedPosition).element);
        assertEquals(new Integer(1), hashTableWithFreeList.at(expectedPosition).prev);
        assertEquals(new Integer(5), hashTableWithFreeList.at(expectedPosition).next);
        assertEquals(new Integer(expectedFreeListHeadPosition), hashTableWithFreeList.free);
    }

    @Test
    public void shouldInsertOntoTakenPositionWithNoneligibleElementInHashTableWithFreeList() {
        // given
        HashTableWithFreeList<String> hashTableWithFreeList = getExemplaryHashTableWithFreeList();
        Element<String> element = new Element<>(13, "thirteen");
        int expectedPosition = 5;
        int expectedFreeListHeadPosition = 0;
        int freeListPosition = 3;
        Element<String> noneligibleElement = hashTableWithFreeList.at(expectedPosition).element;

        // when
        int actualPosition = Chapter11.inPlaceChainedHashInsert(hashTableWithFreeList, element);

        // then
        assertEquals(expectedPosition, actualPosition);
        assertEquals(element, hashTableWithFreeList.at(expectedPosition).element);
        assertEquals(noneligibleElement, hashTableWithFreeList.at(freeListPosition).element);
        assertEquals(new Integer(expectedFreeListHeadPosition), hashTableWithFreeList.free);
    }

    @Test(expected = RuntimeException.class)
    public void shouldThrowExceptionWhenInsertingIntoFullHashTableWithFreeList() {
        // given
        HashTableWithFreeList<String> hashTableWithFreeList = HashTableWithFreeList.withLengthAndHashFunction(8,
                new HashFunction() {
                    @Override
                    public int compute(int key) {
                        return key % 8;
                    }
                }
        );
        hashTableWithFreeList.free = null;
        Element<String> element = new Element<>(25, "twentyFive");

        try {
            // when
            Chapter11.inPlaceChainedHashInsert(hashTableWithFreeList, element);
        } catch (RuntimeException e) {
            // then
            assertEquals("overflow", e.getMessage());
            throw e;
        }
    }

    @Test
    public void shouldFindElementInHashTableWithFreeList() {
        // given
        HashTableWithFreeList<String> hashTableWithFreeList = getExemplaryHashTableWithFreeList();
        int key = 9;
        int expectedPosition = 5;

        // when
        Integer actualPosition = Chapter11.inPlaceChainedHashSearch(hashTableWithFreeList, key);

        // then
        assertEquals(new Integer(expectedPosition), actualPosition);
    }

    @Test
    public void shouldNotFindNonexistentElementInHashTableWithFreeList() {
        // given
        HashTableWithFreeList<String> hashTableWithFreeList = getExemplaryHashTableWithFreeList();
        int key = 33;

        // when
        Integer actualPosition = Chapter11.inPlaceChainedHashSearch(hashTableWithFreeList, key);

        // then
        assertNull(actualPosition);
    }

    @Test
    public void shouldDeleteFromHashTableWithFreeList() {
        // given
        HashTableWithFreeList<String> hashTableWithFreeList = getExemplaryHashTableWithFreeList();
        int position = 5;
        int freeListPosition = hashTableWithFreeList.free;

        // when
        Chapter11.inPlaceChainedHashDelete(hashTableWithFreeList, position);

        // then
        assertNull(hashTableWithFreeList.at(position).element);
        assertNull(hashTableWithFreeList.at(position).prev);
        assertEquals(new Integer(freeListPosition), hashTableWithFreeList.at(position).next);
        assertEquals(new Integer(position), hashTableWithFreeList.free);
    }

    @Test
    public void shouldInsertIntoHashTableWithProbing() {
        // given
        HashTableWithProbing hashTableWithProbing = HashTableWithProbing.withLengthAndHashFunction(5,
                new HashProbingFunction() {
                    @Override
                    public int compute(int key, int i) {
                        int primaryHashValue = key % 5;
                        return (primaryHashValue + i) % 5;
                    }
                }
        );
        hashTableWithProbing.set(new ZeroBasedIndexedArray<>(35,51,null,38,null));
        int key = 45;

        // when
        int actualPosition = Chapter11.hashInsert(hashTableWithProbing, key);

        // then
        assertEquals(2, actualPosition);
        assertEquals(new Integer(key), hashTableWithProbing.at(2));
    }

    @Test(expected = RuntimeException.class)
    public void shouldThrowExceptionWhenInsertingIntoFullHashTableWithProbing() throws Exception {
        // given
        HashTableWithProbing hashTableWithProbing = HashTableWithProbing.withLengthAndHashFunction(5,
                new HashProbingFunction() {
                    @Override
                    public int compute(int key, int i) {
                        int primaryHashValue = key % 5;
                        return (primaryHashValue + i) % 5;
                    }
                }
        );
        hashTableWithProbing.set(new ZeroBasedIndexedArray<>(35,51,45,38,16));
        int key = 32;

        try {
            // when
            Chapter11.hashInsert(hashTableWithProbing, key);
        } catch (RuntimeException e) {
            // then
            assertEquals("hash table overflow", e.getMessage());
            throw e;
        }
    }

    @Test
    public void shouldFindElementInHashTableWithProbing() {
        // given
        HashTableWithProbing hashTableWithProbing = HashTableWithProbing.withLengthAndHashFunction(5,
                new HashProbingFunction() {
                    @Override
                    public int compute(int key, int i) {
                        int primaryHashValue = key % 5;
                        return (primaryHashValue + i) % 5;
                    }
                }
        );
        hashTableWithProbing.set(new ZeroBasedIndexedArray<>(35,51,45,38,null));
        int key = 45;

        // when
        Integer actualPosition = Chapter11.hashSearch(hashTableWithProbing, key);

        // then
        assertEquals(new Integer(2), actualPosition);
    }

    @Test
    public void shouldNotFindNonexistentElementInHashTableWithProbing() {
        // given
        HashTableWithProbing hashTableWithProbing = HashTableWithProbing.withLengthAndHashFunction(5,
                new HashProbingFunction() {
                    @Override
                    public int compute(int key, int i) {
                        int primaryHashValue = key % 5;
                        return (primaryHashValue + i) % 5;
                    }
                }
        );
        hashTableWithProbing.set(new ZeroBasedIndexedArray<>(35,51,45,38,null));
        int key = 26;

        // when
        Integer actualPosition = Chapter11.hashSearch(hashTableWithProbing, key);

        // then
        assertNull(actualPosition);
    }

    @Test
    public void shouldDeleteFromHashTableWithProbing() {
        // given
        HashTableWithProbing hashTableWithProbing = HashTableWithProbing.withLengthAndHashFunction(5,
                new HashProbingFunction() {
                    @Override
                    public int compute(int key, int i) {
                        int primaryHashValue = key % 5;
                        return (primaryHashValue + i) % 5;
                    }
                }
        );
        hashTableWithProbing.set(new ZeroBasedIndexedArray<>(35,51,45,38,null));
        int key = 45;

        // when
        Chapter11.hashDelete(hashTableWithProbing, key);

        // then
        assertEquals(DELETED, hashTableWithProbing.at(2));
    }

    @Test
    public void shouldInsertIntoHashTableWithProbingUsingHashInsert_() {
        // given
        HashTableWithProbing hashTableWithProbing = HashTableWithProbing.withLengthAndHashFunction(5,
                new HashProbingFunction() {
                    @Override
                    public int compute(int key, int i) {
                        int primaryHashValue = key % 5;
                        return (primaryHashValue + i) % 5;
                    }
                }
        );
        hashTableWithProbing.set(new ZeroBasedIndexedArray<>(35,51,DELETED,38,DELETED));
        int key = 45;

        // when
        int actualPosition = Chapter11.hashInsert_(hashTableWithProbing, key);

        // then
        assertEquals(2, actualPosition);
        assertEquals(new Integer(key), hashTableWithProbing.at(2));
    }

    @Test(expected = RuntimeException.class)
    public void shouldThrowExceptionWhenInsertingIntoFullHashTableWithProbingUsingHashInsert_() {
        // given
        HashTableWithProbing hashTableWithProbing = HashTableWithProbing.withLengthAndHashFunction(5,
                new HashProbingFunction() {
                    @Override
                    public int compute(int key, int i) {
                        int primaryHashValue = key % 5;
                        return (primaryHashValue + i) % 5;
                    }
                }
        );
        hashTableWithProbing.set(new ZeroBasedIndexedArray<>(35,51,45,38,16));
        int key = 32;

        try {
            // when
            Chapter11.hashInsert_(hashTableWithProbing, key);
        } catch (RuntimeException e) {
            // then
            assertEquals("hash table overflow", e.getMessage());
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
