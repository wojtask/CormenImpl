package pl.kwojtas.cormenimpl;

import org.junit.Test;
import pl.kwojtas.cormenimpl.util.ChainedHashTable;
import pl.kwojtas.cormenimpl.util.DirectAddressTable;
import pl.kwojtas.cormenimpl.util.HashFunction;
import pl.kwojtas.cormenimpl.util.HashProbingFunction;
import pl.kwojtas.cormenimpl.util.HashTableWithFreeList;
import pl.kwojtas.cormenimpl.util.HashTableWithOpenAddressing;
import pl.kwojtas.cormenimpl.util.HugeArray;
import pl.kwojtas.cormenimpl.util.ZeroBasedIndexedArray;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static pl.kwojtas.cormenimpl.Chapter11.DELETED;

public class Chapter11Test {

    @Test
    public void shouldHavePrivateConstructor() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Constructor<Chapter11> constructor = Chapter11.class.getDeclaredConstructor();
        assertTrue(Modifier.isPrivate(constructor.getModifiers()));
        constructor.setAccessible(true);
        constructor.newInstance();
    }

    private DirectAddressTable<String> getExemplaryDirectAddressTable() {
        DirectAddressTable<String> directAddressTable = new DirectAddressTable<>(5);
        directAddressTable.set(0, new DirectAddressTable.Element<>(0, "zero"));
        directAddressTable.set(1, new DirectAddressTable.Element<>(1, "one"));
        directAddressTable.set(3, new DirectAddressTable.Element<>(3, "three"));
        return directAddressTable;
    }

    @Test
    public void shouldFindElementInDirectAddressTable() {
        // given
        DirectAddressTable<String> directAddressTable = getExemplaryDirectAddressTable();
        DirectAddressTable.Element<String> element = new DirectAddressTable.Element<>(3, "three");

        // when
        DirectAddressTable.Element<String> actualFoundElement = Chapter11.directAddressSearch(directAddressTable, element.key);

        // then
        assertEquals(element.key, actualFoundElement.key);
        assertEquals(element.data, actualFoundElement.data);
    }

    @Test
    public void shouldNotFindNonexistentElementInDirectAddressTable() {
        // given
        DirectAddressTable<String> directAddressTable = getExemplaryDirectAddressTable();
        int key = 2;

        // when
        DirectAddressTable.Element<String> actualFoundElement = Chapter11.directAddressSearch(directAddressTable, key);

        // then
        assertNull(actualFoundElement);
    }

    @Test
    public void shouldInsertIntoDirectAddressTable() {
        // given
        DirectAddressTable<String> directAddressTable = getExemplaryDirectAddressTable();
        DirectAddressTable.Element<String> element = new DirectAddressTable.Element<>(4, "four");
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
        DirectAddressTable<String> directAddressTable = getExemplaryDirectAddressTable();

        // when
        Chapter11.directAddressDelete(directAddressTable, directAddressTable.at(1));

        // then
        assertNull(directAddressTable.at(1));
    }

    @Test
    public void shouldFindMaximumInDirectAddressTable() {
        // given
        DirectAddressTable<String> directAddressTable = getExemplaryDirectAddressTable();
        DirectAddressTable.Element<String> expectedMaximum = new DirectAddressTable.Element<>(3, "three");

        // when
        DirectAddressTable.Element<String> actualMaximum = Chapter11.directAddressMaximum(directAddressTable);

        // then
        assertNotNull(actualMaximum);
        assertEquals(expectedMaximum.key, actualMaximum.key);
        assertEquals(expectedMaximum.data, actualMaximum.data);
    }

    @Test
    public void shouldNotFindMaximumInEmptyDirectAddressTable() {
        // given
        DirectAddressTable<String> directAddressTable = new DirectAddressTable<>(5);

        // when
        DirectAddressTable.Element<String> actualMaximum = Chapter11.directAddressMaximum(directAddressTable);

        // then
        assertNull(actualMaximum);
    }

    @Test
    public void shouldFindElementInBitVector() {
        // given
        ZeroBasedIndexedArray<Integer> bitVector = new ZeroBasedIndexedArray<>(1, 1, 0, 1, 0);
        int key = 3;

        // when
        int actualFound = Chapter11.bitVectorSearch(bitVector, key);

        // then
        assertEquals(1, actualFound);
    }

    @Test
    public void shouldNotFindNonexistentElementInBitVector() {
        // given
        ZeroBasedIndexedArray<Integer> bitVector = new ZeroBasedIndexedArray<>(1, 1, 0, 1, 0);
        int key = 2;

        // when
        int actualFound = Chapter11.bitVectorSearch(bitVector, key);

        // then
        assertEquals(0, actualFound);
    }

    @Test
    public void shouldInsertIntoBitVector() {
        // given
        ZeroBasedIndexedArray<Integer> bitVector = new ZeroBasedIndexedArray<>(1, 1, 0, 1, 0);
        int key = 4;

        // when
        Chapter11.bitVectorInsert(bitVector, key);

        // then
        assertEquals(Integer.valueOf(1), bitVector.at(key));
    }

    @Test
    public void shouldDeleteFromBitVector() {
        // given
        ZeroBasedIndexedArray<Integer> bitVector = new ZeroBasedIndexedArray<>(1, 1, 0, 1, 0);
        int key = 1;

        // when
        Chapter11.bitVectorDelete(bitVector, key);

        // then
        assertEquals(Integer.valueOf(0), bitVector.at(key));
    }

    private DirectAddressTable<String> getExemplaryDirectAddressTableWithNonDistinctKeys() {
        DirectAddressTable<String> directAddressTable = new DirectAddressTable<>(5);
        DirectAddressTable.Element<String> x0 = new DirectAddressTable.Element<>(0, "zero");
        DirectAddressTable.Element<String> x1a = new DirectAddressTable.Element<>(1, "oneA");
        DirectAddressTable.Element<String> x1b = new DirectAddressTable.Element<>(1, "oneB");
        DirectAddressTable.Element<String> x1c = new DirectAddressTable.Element<>(1, "oneC");
        DirectAddressTable.Element<String> x3a = new DirectAddressTable.Element<>(3, "threeA");
        DirectAddressTable.Element<String> x3b = new DirectAddressTable.Element<>(1, "threeB");
        directAddressTable.set(0, x0);
        directAddressTable.set(1, x1a);
        x1a.next = x1b;
        x1b.prev = x1a;
        x1b.next = x1c;
        x1c.prev = x1b;
        directAddressTable.set(3, x3a);
        x3a.next = x3b;
        x3b.prev = x3a;
        return directAddressTable;
    }

    @Test
    public void shouldFindElementInDirectAddressTableWithNonDistinctKeys() {
        // given
        DirectAddressTable<String> directAddressTable = getExemplaryDirectAddressTableWithNonDistinctKeys();
        DirectAddressTable.Element<String> element = new DirectAddressTable.Element<>(3, "threeA");

        // when
        DirectAddressTable.Element<String> actualFoundElement = Chapter11.directAddressSearch_(directAddressTable, element.key);

        // then
        assertNotNull(actualFoundElement);
        assertEquals(element.key, actualFoundElement.key);
        assertEquals(element.data, actualFoundElement.data);
    }

    @Test
    public void shouldNotFindNonexistentElementInDirectAddressTableWithNonDistinctKeys() {
        // given
        DirectAddressTable<String> directAddressTable = getExemplaryDirectAddressTableWithNonDistinctKeys();
        int key = 2;

        // when
        DirectAddressTable.Element<String> actualFoundElement = Chapter11.directAddressSearch_(directAddressTable, key);

        // then
        assertNull(actualFoundElement);
    }

    @Test
    public void shouldInsertIntoDirectAddressTableWithNonDistinctKeys() {
        // given
        DirectAddressTable<String> directAddressTable = getExemplaryDirectAddressTableWithNonDistinctKeys();
        DirectAddressTable.Element<String> element = new DirectAddressTable.Element<>(4, "four");

        // when
        Chapter11.directAddressInsert_(directAddressTable, element);

        // then
        DirectAddressTable.Element<String> actualElement = directAddressTable.at(element.key);
        assertEquals(element.key, actualElement.key);
        assertEquals(element.data, actualElement.data);
    }

    @Test
    public void shouldDeleteFromDirectAddressTableWithNonDistinctKeys() {
        // given
        DirectAddressTable<String> directAddressTable = getExemplaryDirectAddressTableWithNonDistinctKeys();
        int key = 1;

        // when
        Chapter11.directAddressDelete_(directAddressTable, directAddressTable.at(key).next);

        // then
        assertEquals("oneC", directAddressTable.at(key).next.data);
    }

    private HugeArray<String> getExemplaryHugeArray() {
        HugeArray<String> hugeArray = new HugeArray<>(100, 5);
        hugeArray.T.set(56, 1);
        hugeArray.T.set(23, 2);
        hugeArray.T.set(10, 3);
        hugeArray.S.set(1, new HugeArray.Element<>(56, "fiftySix"));
        hugeArray.S.set(2, new HugeArray.Element<>(23, "twentyThree"));
        hugeArray.S.set(3, new HugeArray.Element<>(10, "ten"));
        hugeArray.S.top = 3;
        return hugeArray;
    }

    @Test
    public void shouldFindElementInHugeArray() {
        // given
        HugeArray<String> hugeArray = getExemplaryHugeArray();
        HugeArray.Element<String> element = new HugeArray.Element<>(23, "twentyThree");

        // when
        HugeArray.Element<String> actualFoundElement = Chapter11.hugeArraySearch(hugeArray, element.key);

        // then
        assertNotNull(actualFoundElement);
        assertEquals(element.key, actualFoundElement.key);
        assertEquals(element.data, actualFoundElement.data);
    }

    @Test
    public void shouldNotFindNonexistentElementInHugeArray() {
        // given
        HugeArray<String> hugeArray = getExemplaryHugeArray();
        int key = 66;

        // when
        HugeArray.Element<String> actualFoundElement = Chapter11.hugeArraySearch(hugeArray, key);

        // then
        assertNull(actualFoundElement);
    }

    @Test
    public void shouldInsertIntoHugeArray() {
        // given
        HugeArray<String> hugeArray = getExemplaryHugeArray();
        HugeArray.Element<String> element = new HugeArray.Element<>(66, "sixtySix");
        int hugeArraySize = hugeArray.S.top;

        // when
        Chapter11.hugeArrayInsert(hugeArray, element);

        // then
        assertEquals(hugeArraySize + 1, hugeArray.S.top);
        assertEquals(element.key, hugeArray.S.at(hugeArraySize + 1).key);
        assertEquals(element.data, hugeArray.S.at(hugeArraySize + 1).data);
        assertEquals(Integer.valueOf(hugeArraySize + 1), hugeArray.T.at(element.key));
    }

    @Test
    public void shouldDeleteFromHugeArray() {
        // given
        HugeArray<String> hugeArray = getExemplaryHugeArray();
        HugeArray.Element<String> element = new HugeArray.Element<>(56, "fiftySix");
        int hugeArraySize = hugeArray.S.top;

        // when
        Chapter11.hugeArrayDelete(hugeArray, element);

        // then
        assertEquals(hugeArraySize - 1, hugeArray.S.top);
        assertEquals(10, hugeArray.S.at(1).key);
        assertEquals("ten", hugeArray.S.at(1).data);
        assertEquals(Integer.valueOf(1), hugeArray.T.at(10));
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
        ChainedHashTable.Element<String> x35 = new ChainedHashTable.Element<>(35, "thirtyFive");
        ChainedHashTable.Element<String> x51 = new ChainedHashTable.Element<>(51, "fiftyOne");
        ChainedHashTable.Element<String> x16 = new ChainedHashTable.Element<>(16, "sixteen");
        ChainedHashTable.Element<String> x1 = new ChainedHashTable.Element<>(1, "one");
        ChainedHashTable.Element<String> x38 = new ChainedHashTable.Element<>(38, "thirtyEight");
        ChainedHashTable.Element<String> x23 = new ChainedHashTable.Element<>(23, "twentyThree");
        chainedHashTable.set(0, x35);
        chainedHashTable.set(1, x51);
        x51.next = x16;
        x16.prev = x51;
        x16.next = x1;
        x1.prev = x16;
        chainedHashTable.set(3, x38);
        x38.next = x23;
        x23.prev = x38;
        return chainedHashTable;
    }

    @Test
    public void shouldInsertIntoChainedHashTable() {
        // given
        ChainedHashTable<String> chainedHashTable = getExemplaryChainedHashTable();
        ChainedHashTable.Element<String> element = new ChainedHashTable.Element<>(64, "sixtyFour");
        int hash = chainedHashTable.h.compute(element.key);

        // when
        Chapter11.chainedHashInsert(chainedHashTable, element);

        // then
        assertEquals(element.key, chainedHashTable.at(hash).key);
        assertEquals(element.data, chainedHashTable.at(hash).data);
    }

    @Test
    public void shouldFindElementInChainedHashTable() {
        // given
        ChainedHashTable<String> chainedHashTable = getExemplaryChainedHashTable();
        ChainedHashTable.Element<String> element = new ChainedHashTable.Element<>(23, "twentyThree");

        // when
        ChainedHashTable.Element<String> actualFoundElement = Chapter11.chainedHashSearch(chainedHashTable, element.key);

        // then
        assertNotNull(actualFoundElement);
        assertEquals(element.key, actualFoundElement.key);
        assertEquals(element.data, actualFoundElement.data);
    }

    @Test
    public void shouldNotFindNonexistentElementInChainedHashTable() {
        // given
        ChainedHashTable<String> chainedHashTable = getExemplaryChainedHashTable();
        int key = 42;

        // when
        ChainedHashTable.Element<String> actualFoundElement = Chapter11.chainedHashSearch(chainedHashTable, key);

        // then
        assertNull(actualFoundElement);
    }

    @Test
    public void shouldDeleteFromChainedHashTable() {
        // given
        ChainedHashTable<String> chainedHashTable = getExemplaryChainedHashTable();
        ChainedHashTable.Element<String> element = chainedHashTable.at(1).next;
        int expectedKey = chainedHashTable.at(1).next.next.key;

        // when
        Chapter11.chainedHashDelete(chainedHashTable, element);

        // then
        assertEquals(expectedKey, chainedHashTable.at(1).next.key);
    }

    @Test
    public void shouldInsertOntoFreePositionInHashTableWithFreeList() {
        // given
        HashTableWithFreeList<String> hashTableWithFreeList = getExemplaryHashTableWithFreeList();
        HashTableWithFreeList.Element<String> element = new HashTableWithFreeList.Element<>(43, "fortyThree");
        int expectedPosition = 3;
        int expectedFreeListHeadPosition = 0;

        // when
        int actualPosition = Chapter11.inPlaceChainedHashInsert(hashTableWithFreeList, element);

        // then
        assertEquals(expectedPosition, actualPosition);
        assertEquals(element, hashTableWithFreeList.at(expectedPosition));
        assertNull(hashTableWithFreeList.at(expectedPosition).prev);
        assertNull(hashTableWithFreeList.at(expectedPosition).next);
        assertEquals(Integer.valueOf(expectedFreeListHeadPosition), hashTableWithFreeList.free);
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
        hashTableWithFreeList.at(1).key = 17;
        hashTableWithFreeList.at(1).data = "seventeen";
        hashTableWithFreeList.at(1).next = 5;
        hashTableWithFreeList.at(2).key = 23;
        hashTableWithFreeList.at(2).data = "twentyThree";
        hashTableWithFreeList.at(2).prev = 7;
        hashTableWithFreeList.at(3).next = 0;
        hashTableWithFreeList.at(4).key = 1;
        hashTableWithFreeList.at(4).data = "one";
        hashTableWithFreeList.at(4).prev = 5;
        hashTableWithFreeList.at(5).key = 9;
        hashTableWithFreeList.at(5).data = "nine";
        hashTableWithFreeList.at(5).next = 4;
        hashTableWithFreeList.at(5).prev = 1;
        hashTableWithFreeList.at(6).key = 6;
        hashTableWithFreeList.at(6).data = "six";
        hashTableWithFreeList.at(7).key = 15;
        hashTableWithFreeList.at(7).data = "fifteen";
        hashTableWithFreeList.at(7).next = 2;
        return hashTableWithFreeList;
    }

    @Test
    public void shouldInsertOntoTakenPositionWithEligibleElementInHashTableWithFreeList() {
        // given
        HashTableWithFreeList<String> hashTableWithFreeList = getExemplaryHashTableWithFreeList();
        HashTableWithFreeList.Element<String> element = new HashTableWithFreeList.Element<>(25, "twentyFive");
        int expectedPosition = 3;
        int expectedFreeListHeadPosition = 0;

        // when
        int actualPosition = Chapter11.inPlaceChainedHashInsert(hashTableWithFreeList, element);

        // then
        assertEquals(expectedPosition, actualPosition);
        assertEquals(element, hashTableWithFreeList.at(expectedPosition));
        assertEquals(Integer.valueOf(1), hashTableWithFreeList.at(expectedPosition).prev);
        assertEquals(Integer.valueOf(5), hashTableWithFreeList.at(expectedPosition).next);
        assertEquals(Integer.valueOf(expectedFreeListHeadPosition), hashTableWithFreeList.free);
    }

    @Test
    public void shouldInsertOntoTakenPositionWithNoneligibleElementInHashTableWithFreeList() {
        // given
        HashTableWithFreeList<String> hashTableWithFreeList = getExemplaryHashTableWithFreeList();
        HashTableWithFreeList.Element<String> element = new HashTableWithFreeList.Element<>(13, "thirteen");
        int expectedPosition = 5;
        int expectedFreeListHeadPosition = 0;
        int freeListPosition = 3;
        HashTableWithFreeList.Element<String> noneligibleElement = hashTableWithFreeList.at(expectedPosition);

        // when
        int actualPosition = Chapter11.inPlaceChainedHashInsert(hashTableWithFreeList, element);

        // then
        assertEquals(expectedPosition, actualPosition);
        assertEquals(element, hashTableWithFreeList.at(expectedPosition));
        assertEquals(noneligibleElement, hashTableWithFreeList.at(freeListPosition));
        assertEquals(Integer.valueOf(expectedFreeListHeadPosition), hashTableWithFreeList.free);
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
        HashTableWithFreeList.Element<String> element = new HashTableWithFreeList.Element<>(25, "twentyFive");

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
        assertEquals(Integer.valueOf(expectedPosition), actualPosition);
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
        assertNull(hashTableWithFreeList.at(position).prev);
        assertEquals(Integer.valueOf(freeListPosition), hashTableWithFreeList.at(position).next);
        assertEquals(Integer.valueOf(position), hashTableWithFreeList.free);
    }

    @Test
    public void shouldInsertIntoHashTableWithProbing() {
        // given
        HashTableWithOpenAddressing hashTableWithOpenAddressing = HashTableWithOpenAddressing.withLengthAndHashFunction(5,
                new HashProbingFunction() {
                    @Override
                    public int compute(int key, int i) {
                        int primaryHashValue = key % 5;
                        return (primaryHashValue + i) % 5;
                    }
                }
        );
        hashTableWithOpenAddressing.set(new ZeroBasedIndexedArray<>(35, 51, null, 38, null));
        int key = 45;

        // when
        int actualPosition = Chapter11.hashInsert(hashTableWithOpenAddressing, key);

        // then
        assertEquals(2, actualPosition);
        assertEquals(Integer.valueOf(key), hashTableWithOpenAddressing.at(2));
    }

    @Test(expected = RuntimeException.class)
    public void shouldThrowExceptionWhenInsertingIntoFullHashTableWithProbing() {
        // given
        HashTableWithOpenAddressing hashTableWithOpenAddressing = HashTableWithOpenAddressing.withLengthAndHashFunction(5,
                new HashProbingFunction() {
                    @Override
                    public int compute(int key, int i) {
                        int primaryHashValue = key % 5;
                        return (primaryHashValue + i) % 5;
                    }
                }
        );
        hashTableWithOpenAddressing.set(new ZeroBasedIndexedArray<>(35, 51, 45, 38, 16));
        int key = 32;

        try {
            // when
            Chapter11.hashInsert(hashTableWithOpenAddressing, key);
        } catch (RuntimeException e) {
            // then
            assertEquals("hash table overflow", e.getMessage());
            throw e;
        }
    }

    @Test
    public void shouldFindElementInHashTableWithProbing() {
        // given
        HashTableWithOpenAddressing hashTableWithOpenAddressing = HashTableWithOpenAddressing.withLengthAndHashFunction(5,
                new HashProbingFunction() {
                    @Override
                    public int compute(int key, int i) {
                        int primaryHashValue = key % 5;
                        return (primaryHashValue + i) % 5;
                    }
                }
        );
        hashTableWithOpenAddressing.set(new ZeroBasedIndexedArray<>(35, 51, 45, 38, null));
        int key = 45;

        // when
        Integer actualPosition = Chapter11.hashSearch(hashTableWithOpenAddressing, key);

        // then
        assertEquals(Integer.valueOf(2), actualPosition);
    }

    @Test
    public void shouldNotFindNonexistentElementInHashTableWithProbing() {
        // given
        HashTableWithOpenAddressing hashTableWithOpenAddressing = HashTableWithOpenAddressing.withLengthAndHashFunction(5,
                new HashProbingFunction() {
                    @Override
                    public int compute(int key, int i) {
                        int primaryHashValue = key % 5;
                        return (primaryHashValue + i) % 5;
                    }
                }
        );
        hashTableWithOpenAddressing.set(new ZeroBasedIndexedArray<>(35, 51, 45, 38, null));
        int key = 26;

        // when
        Integer actualPosition = Chapter11.hashSearch(hashTableWithOpenAddressing, key);

        // then
        assertNull(actualPosition);
    }

    @Test
    public void shouldDeleteFromHashTableWithProbing() {
        // given
        HashTableWithOpenAddressing hashTableWithOpenAddressing = HashTableWithOpenAddressing.withLengthAndHashFunction(5,
                new HashProbingFunction() {
                    @Override
                    public int compute(int key, int i) {
                        int primaryHashValue = key % 5;
                        return (primaryHashValue + i) % 5;
                    }
                }
        );
        hashTableWithOpenAddressing.set(new ZeroBasedIndexedArray<>(35, 51, 45, 38, null));
        int key = 45;

        // when
        Chapter11.hashDelete(hashTableWithOpenAddressing, key);

        // then
        assertEquals(DELETED, hashTableWithOpenAddressing.at(2));
    }

    @Test
    public void shouldNotDeleteNonexistentElementFromHashTableWithProbing() {
        // given
        HashTableWithOpenAddressing hashTableWithOpenAddressing = HashTableWithOpenAddressing.withLengthAndHashFunction(5,
                new HashProbingFunction() {
                    @Override
                    public int compute(int key, int i) {
                        int primaryHashValue = key % 5;
                        return (primaryHashValue + i) % 5;
                    }
                }
        );
        hashTableWithOpenAddressing.set(new ZeroBasedIndexedArray<>(35, 51, 45, 38, 3));
        int key = 23;

        // when
        Chapter11.hashDelete(hashTableWithOpenAddressing, key);

        // then
        for (int i = 0; i <= hashTableWithOpenAddressing.length - 1; i++) {
            assertNotEquals(DELETED, hashTableWithOpenAddressing.at(i));
        }
    }

    @Test
    public void shouldInsertIntoHashTableWithProbingUsingHashInsert_() {
        // given
        HashTableWithOpenAddressing hashTableWithOpenAddressing = HashTableWithOpenAddressing.withLengthAndHashFunction(5,
                new HashProbingFunction() {
                    @Override
                    public int compute(int key, int i) {
                        int primaryHashValue = key % 5;
                        return (primaryHashValue + i) % 5;
                    }
                }
        );
        hashTableWithOpenAddressing.set(new ZeroBasedIndexedArray<>(35, 51, DELETED, 38, DELETED));
        int key = 45;

        // when
        int actualPosition = Chapter11.hashInsert_(hashTableWithOpenAddressing, key);

        // then
        assertEquals(2, actualPosition);
        assertEquals(Integer.valueOf(key), hashTableWithOpenAddressing.at(2));
    }

    @Test(expected = RuntimeException.class)
    public void shouldThrowExceptionWhenInsertingIntoFullHashTableWithProbingUsingHashInsert_() {
        // given
        HashTableWithOpenAddressing hashTableWithOpenAddressing = HashTableWithOpenAddressing.withLengthAndHashFunction(5,
                new HashProbingFunction() {
                    @Override
                    public int compute(int key, int i) {
                        int primaryHashValue = key % 5;
                        return (primaryHashValue + i) % 5;
                    }
                }
        );
        hashTableWithOpenAddressing.set(new ZeroBasedIndexedArray<>(35, 51, 45, 38, 16));
        int key = 32;

        try {
            // when
            Chapter11.hashInsert_(hashTableWithOpenAddressing, key);
        } catch (RuntimeException e) {
            // then
            assertEquals("hash table overflow", e.getMessage());
            throw e;
        }
    }

    @Test
    public void shouldFindElementUsingQuadraticProbing() {
        // given
        ZeroBasedIndexedArray<Integer> hashTable = new ZeroBasedIndexedArray<>(24, null, 34, 35, 51, null, 27, null);
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
        assertEquals(Integer.valueOf(6), actualPosition);
    }

    @Test
    public void shouldNotFindNonexistentElementUsingQuadraticProbing() {
        // given
        ZeroBasedIndexedArray<Integer> hashTable = new ZeroBasedIndexedArray<>(24, null, 34, 35, 51, null, 27, null);
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

    @Test
    public void shouldNotFindNonexistentElementInFullHashTableUsingQuadraticProbing() {
        // given
        ZeroBasedIndexedArray<Integer> hashTable = new ZeroBasedIndexedArray<>(24, 9, 34, 35, 51, 13, 27, 20);
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
