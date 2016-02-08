package pl.kwojtas.cormenimpl;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import pl.kwojtas.cormenimpl.datastructure.ChainedHashTable;
import pl.kwojtas.cormenimpl.datastructure.DirectAddressTable;
import pl.kwojtas.cormenimpl.datastructure.HashFunction;
import pl.kwojtas.cormenimpl.datastructure.HashProbingFunction;
import pl.kwojtas.cormenimpl.datastructure.HashTableWithFreeList;
import pl.kwojtas.cormenimpl.datastructure.HashTableWithOpenAddressing;
import pl.kwojtas.cormenimpl.datastructure.HugeArray;
import pl.kwojtas.cormenimpl.datastructure.ZeroBasedIndexedArray;

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

    @Rule
    public ExpectedException thrown = ExpectedException.none();

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
        DirectAddressTable<String> directAddressTable = getExemplaryDirectAddressTable();
        DirectAddressTable.Element<String> element = new DirectAddressTable.Element<>(3, "three");

        DirectAddressTable.Element<String> actualFoundElement = Chapter11.directAddressSearch(directAddressTable, element.key);

        assertEquals(element.key, actualFoundElement.key);
        assertEquals(element.data, actualFoundElement.data);
    }

    @Test
    public void shouldNotFindNonexistentElementInDirectAddressTable() {
        DirectAddressTable<String> directAddressTable = getExemplaryDirectAddressTable();
        int key = 2;

        DirectAddressTable.Element<String> actualFoundElement = Chapter11.directAddressSearch(directAddressTable, key);

        assertNull(actualFoundElement);
    }

    @Test
    public void shouldInsertIntoDirectAddressTable() {
        DirectAddressTable<String> directAddressTable = getExemplaryDirectAddressTable();
        DirectAddressTable.Element<String> element = new DirectAddressTable.Element<>(4, "four");
        int key = 4;

        Chapter11.directAddressInsert(directAddressTable, element);

        assertEquals(element.key, directAddressTable.at(key).key);
        assertEquals(element.data, directAddressTable.at(key).data);
    }

    @Test
    public void shouldDeleteFromDirectAddressTable() {
        DirectAddressTable<String> directAddressTable = getExemplaryDirectAddressTable();

        Chapter11.directAddressDelete(directAddressTable, directAddressTable.at(1));

        assertNull(directAddressTable.at(1));
    }

    @Test
    public void shouldFindMaximumInDirectAddressTable() {
        DirectAddressTable<String> directAddressTable = getExemplaryDirectAddressTable();
        DirectAddressTable.Element<String> expectedMaximum = new DirectAddressTable.Element<>(3, "three");

        DirectAddressTable.Element<String> actualMaximum = Chapter11.directAddressMaximum(directAddressTable);

        assertNotNull(actualMaximum);
        assertEquals(expectedMaximum.key, actualMaximum.key);
        assertEquals(expectedMaximum.data, actualMaximum.data);
    }

    @Test
    public void shouldNotFindMaximumInEmptyDirectAddressTable() {
        DirectAddressTable<String> directAddressTable = new DirectAddressTable<>(5);

        DirectAddressTable.Element<String> actualMaximum = Chapter11.directAddressMaximum(directAddressTable);

        assertNull(actualMaximum);
    }

    @Test
    public void shouldFindElementInBitVector() {
        ZeroBasedIndexedArray<Integer> bitVector = new ZeroBasedIndexedArray<>(1, 1, 0, 1, 0);
        int key = 3;

        int actualFound = Chapter11.bitVectorSearch(bitVector, key);

        assertEquals(1, actualFound);
    }

    @Test
    public void shouldNotFindNonexistentElementInBitVector() {
        ZeroBasedIndexedArray<Integer> bitVector = new ZeroBasedIndexedArray<>(1, 1, 0, 1, 0);
        int key = 2;

        int actualFound = Chapter11.bitVectorSearch(bitVector, key);

        assertEquals(0, actualFound);
    }

    @Test
    public void shouldInsertIntoBitVector() {
        ZeroBasedIndexedArray<Integer> bitVector = new ZeroBasedIndexedArray<>(1, 1, 0, 1, 0);
        int key = 4;

        Chapter11.bitVectorInsert(bitVector, key);

        assertEquals(Integer.valueOf(1), bitVector.at(key));
    }

    @Test
    public void shouldDeleteFromBitVector() {
        ZeroBasedIndexedArray<Integer> bitVector = new ZeroBasedIndexedArray<>(1, 1, 0, 1, 0);
        int key = 1;

        Chapter11.bitVectorDelete(bitVector, key);

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
        DirectAddressTable<String> directAddressTable = getExemplaryDirectAddressTableWithNonDistinctKeys();
        DirectAddressTable.Element<String> element = new DirectAddressTable.Element<>(3, "threeA");

        DirectAddressTable.Element<String> actualFoundElement = Chapter11.directAddressSearch_(directAddressTable, element.key);

        assertNotNull(actualFoundElement);
        assertEquals(element.key, actualFoundElement.key);
        assertEquals(element.data, actualFoundElement.data);
    }

    @Test
    public void shouldNotFindNonexistentElementInDirectAddressTableWithNonDistinctKeys() {
        DirectAddressTable<String> directAddressTable = getExemplaryDirectAddressTableWithNonDistinctKeys();
        int key = 2;

        DirectAddressTable.Element<String> actualFoundElement = Chapter11.directAddressSearch_(directAddressTable, key);

        assertNull(actualFoundElement);
    }

    @Test
    public void shouldInsertIntoDirectAddressTableWithNonDistinctKeys() {
        DirectAddressTable<String> directAddressTable = getExemplaryDirectAddressTableWithNonDistinctKeys();
        DirectAddressTable.Element<String> element = new DirectAddressTable.Element<>(4, "four");

        Chapter11.directAddressInsert_(directAddressTable, element);

        DirectAddressTable.Element<String> actualElement = directAddressTable.at(element.key);
        assertEquals(element.key, actualElement.key);
        assertEquals(element.data, actualElement.data);
    }

    @Test
    public void shouldInsertDuplicateIntoDirectAddressTableWithNonDistinctKeys() {
        DirectAddressTable<String> directAddressTable = getExemplaryDirectAddressTableWithNonDistinctKeys();
        DirectAddressTable.Element<String> newElement = new DirectAddressTable.Element<>(0, "zeroB");
        DirectAddressTable.Element<String> existingElement = new DirectAddressTable.Element<>(0, "zero");

        Chapter11.directAddressInsert_(directAddressTable, newElement);

        DirectAddressTable.Element<String> actualElement = directAddressTable.at(newElement.key);
        assertEquals(newElement.key, actualElement.key);
        assertEquals(newElement.data, actualElement.data);
        assertEquals(existingElement.key, actualElement.next.key);
        assertEquals(existingElement.data, actualElement.next.data);
    }

    @Test
    public void shouldDeleteFromDirectAddressTableWithNonDistinctKeys() {
        DirectAddressTable<String> directAddressTable = getExemplaryDirectAddressTableWithNonDistinctKeys();
        int key = 1;

        Chapter11.directAddressDelete_(directAddressTable, directAddressTable.at(key).next);

        assertEquals("oneC", directAddressTable.at(key).next.data);
    }

    @Test
    public void shouldDeleteElementWithUniqueKeyFromDirectAddressTableWithNonDistinctKeys() {
        DirectAddressTable<String> directAddressTable = getExemplaryDirectAddressTableWithNonDistinctKeys();
        int key = 0;

        Chapter11.directAddressDelete_(directAddressTable, directAddressTable.at(key));

        assertNull(directAddressTable.at(key));
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
        HugeArray<String> hugeArray = getExemplaryHugeArray();
        HugeArray.Element<String> element = new HugeArray.Element<>(23, "twentyThree");

        HugeArray.Element<String> actualFoundElement = Chapter11.hugeArraySearch(hugeArray, element.key);

        assertNotNull(actualFoundElement);
        assertEquals(element.key, actualFoundElement.key);
        assertEquals(element.data, actualFoundElement.data);
    }

    @Test
    public void shouldNotFindNonexistentElementInHugeArray() {
        HugeArray<String> hugeArray = getExemplaryHugeArray();
        int key = 66;

        HugeArray.Element<String> actualFoundElement = Chapter11.hugeArraySearch(hugeArray, key);

        assertNull(actualFoundElement);
    }

    @Test
    public void shouldInsertIntoHugeArray() {
        HugeArray<String> hugeArray = getExemplaryHugeArray();
        HugeArray.Element<String> element = new HugeArray.Element<>(66, "sixtySix");
        int hugeArraySize = hugeArray.S.top;

        Chapter11.hugeArrayInsert(hugeArray, element);

        assertEquals(hugeArraySize + 1, hugeArray.S.top);
        assertEquals(element.key, hugeArray.S.at(hugeArraySize + 1).key);
        assertEquals(element.data, hugeArray.S.at(hugeArraySize + 1).data);
        assertEquals(Integer.valueOf(hugeArraySize + 1), hugeArray.T.at(element.key));
    }

    @Test
    public void shouldDeleteFromHugeArray() {
        HugeArray<String> hugeArray = getExemplaryHugeArray();
        HugeArray.Element<String> element = new HugeArray.Element<>(56, "fiftySix");
        int hugeArraySize = hugeArray.S.top;

        Chapter11.hugeArrayDelete(hugeArray, element);

        assertEquals(hugeArraySize - 1, hugeArray.S.top);
        assertEquals(10, hugeArray.S.at(1).key);
        assertEquals("ten", hugeArray.S.at(1).data);
        assertEquals(Integer.valueOf(1), hugeArray.T.at(10));
    }

    private ChainedHashTable<String> getExemplaryChainedHashTable() {
        ChainedHashTable<String> chainedHashTable = ChainedHashTable.withLengthAndHashFunction(5, key -> key % 5);
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
    public void shouldInsertIntoChainedHashTableWithEmptyChainAtGivenKey() {
        ChainedHashTable<String> chainedHashTable = getExemplaryChainedHashTable();
        ChainedHashTable.Element<String> element = new ChainedHashTable.Element<>(64, "sixtyFour");
        int hash = chainedHashTable.h.compute(element.key);

        Chapter11.chainedHashInsert(chainedHashTable, element);

        assertEquals(element.key, chainedHashTable.at(hash).key);
        assertEquals(element.data, chainedHashTable.at(hash).data);
    }

    @Test
    public void shouldInsertIntoChainedHashTableWithNonemptyChainAtGivenKey() {
        ChainedHashTable<String> chainedHashTable = getExemplaryChainedHashTable();
        ChainedHashTable.Element<String> element = new ChainedHashTable.Element<>(16, "sixteen");
        int hash = chainedHashTable.h.compute(element.key);

        Chapter11.chainedHashInsert(chainedHashTable, element);

        assertEquals(element.key, chainedHashTable.at(hash).key);
        assertEquals(element.data, chainedHashTable.at(hash).data);
    }

    @Test
    public void shouldFindElementInChainedHashTable() {
        ChainedHashTable<String> chainedHashTable = getExemplaryChainedHashTable();
        ChainedHashTable.Element<String> element = new ChainedHashTable.Element<>(23, "twentyThree");

        ChainedHashTable.Element<String> actualFoundElement = Chapter11.chainedHashSearch(chainedHashTable, element.key);

        assertNotNull(actualFoundElement);
        assertEquals(element.key, actualFoundElement.key);
        assertEquals(element.data, actualFoundElement.data);
    }

    @Test
    public void shouldNotFindNonexistentElementInChainedHashTable() {
        ChainedHashTable<String> chainedHashTable = getExemplaryChainedHashTable();
        int key = 42;

        ChainedHashTable.Element<String> actualFoundElement = Chapter11.chainedHashSearch(chainedHashTable, key);

        assertNull(actualFoundElement);
    }

    @Test
    public void shouldDeleteFromChainedHashTable() {
        ChainedHashTable<String> chainedHashTable = getExemplaryChainedHashTable();
        ChainedHashTable.Element<String> element = chainedHashTable.at(1).next;
        int expectedKey = chainedHashTable.at(1).next.next.key;

        Chapter11.chainedHashDelete(chainedHashTable, element);

        assertEquals(expectedKey, chainedHashTable.at(1).next.key);
    }

    @Test
    public void shouldDeleteFromChainedHashTableTheOnlyElementInItsChain() {
        ChainedHashTable<String> chainedHashTable = getExemplaryChainedHashTable();
        ChainedHashTable.Element<String> element = chainedHashTable.at(0);

        Chapter11.chainedHashDelete(chainedHashTable, element);

        assertNull(chainedHashTable.at(0));
    }

    @Test
    public void shouldInsertOntoFreePositionInHashTableWithFreeList() {
        HashTableWithFreeList<String> hashTableWithFreeList = getExemplaryHashTableWithFreeList();
        HashTableWithFreeList.Element<String> element = new HashTableWithFreeList.Element<>(43, "fortyThree");
        int expectedPosition = 3;
        int expectedFreeListHeadPosition = 0;

        int actualPosition = Chapter11.inPlaceChainedHashInsert(hashTableWithFreeList, element);

        assertEquals(expectedPosition, actualPosition);
        assertEquals(element, hashTableWithFreeList.at(expectedPosition).element);
        assertEquals(hashTableWithFreeList.length, hashTableWithFreeList.at(expectedPosition).prev);
        assertEquals(-1, hashTableWithFreeList.at(expectedPosition).next);
        assertEquals(expectedFreeListHeadPosition, hashTableWithFreeList.F);
    }

    @Test
    public void shouldInsertOntoFreePositionInHashTableWithFreeList2() {
        HashTableWithFreeList<String> hashTableWithFreeList = getExemplaryHashTableWithFreeList();
        HashTableWithFreeList.Element<String> element = new HashTableWithFreeList.Element<>(16, "sixteen");
        int expectedPosition = 0;
        int expectedFreeListHeadPosition = 3;

        int actualPosition = Chapter11.inPlaceChainedHashInsert(hashTableWithFreeList, element);

        assertEquals(expectedPosition, actualPosition);
        assertEquals(element, hashTableWithFreeList.at(expectedPosition).element);
        assertEquals(hashTableWithFreeList.length, hashTableWithFreeList.at(expectedPosition).prev);
        assertEquals(-1, hashTableWithFreeList.at(expectedPosition).next);
        assertEquals(expectedFreeListHeadPosition, hashTableWithFreeList.F);
    }

    private HashTableWithFreeList<String> getExemplaryHashTableWithFreeList() {
        HashTableWithFreeList<String> hashTableWithFreeList =
                HashTableWithFreeList.withLengthAndHashFunction(8, key -> key % 8);
        hashTableWithFreeList.F = 3;
        hashTableWithFreeList.at(0).next = -1;
        hashTableWithFreeList.at(0).prev = 3;
        hashTableWithFreeList.at(1).element = new HashTableWithFreeList.Element<>(17, "seventeen");
        hashTableWithFreeList.at(1).next = 5;
        hashTableWithFreeList.at(1).prev = 8;
        hashTableWithFreeList.at(2).element = new HashTableWithFreeList.Element<>(23, "twentyThree");
        hashTableWithFreeList.at(2).next = -1;
        hashTableWithFreeList.at(2).prev = 8;
        hashTableWithFreeList.at(3).next = 0;
        hashTableWithFreeList.at(3).prev = -1;
        hashTableWithFreeList.at(4).element = new HashTableWithFreeList.Element<>(1, "one");
        hashTableWithFreeList.at(4).next = -1;
        hashTableWithFreeList.at(4).prev = 8;
        hashTableWithFreeList.at(5).element = new HashTableWithFreeList.Element<>(9, "nine");
        hashTableWithFreeList.at(5).next = 4;
        hashTableWithFreeList.at(5).prev = 8;
        hashTableWithFreeList.at(6).element = new HashTableWithFreeList.Element<>(6, "six");
        hashTableWithFreeList.at(6).next = -1;
        hashTableWithFreeList.at(6).prev = 8;
        hashTableWithFreeList.at(7).element = new HashTableWithFreeList.Element<>(15, "fifteen");
        hashTableWithFreeList.at(7).next = 2;
        hashTableWithFreeList.at(7).prev = 8;
        return hashTableWithFreeList;
    }

    @Test
    public void shouldInsertOntoTakenPositionWithEligibleElementInHashTableWithFreeList() {
        HashTableWithFreeList<String> hashTableWithFreeList = getExemplaryHashTableWithFreeList();
        HashTableWithFreeList.Element<String> element = new HashTableWithFreeList.Element<>(25, "twentyFive");
        int expectedPosition = 1;
        int expectedFreeListHeadPosition = 0;
        int freeListPosition = hashTableWithFreeList.F;

        int actualPosition = Chapter11.inPlaceChainedHashInsert(hashTableWithFreeList, element);

        assertEquals(expectedPosition, actualPosition);
        assertEquals(element, hashTableWithFreeList.at(expectedPosition).element);
        assertEquals(hashTableWithFreeList.length, hashTableWithFreeList.at(expectedPosition).prev);
        assertEquals(freeListPosition, hashTableWithFreeList.at(expectedPosition).next);
        assertEquals(expectedFreeListHeadPosition, hashTableWithFreeList.F);
    }

    @Test
    public void shouldInsertOntoTakenPositionWithNoneligibleElementInHashTableWithFreeList() {
        HashTableWithFreeList<String> hashTableWithFreeList = getExemplaryHashTableWithFreeList();
        HashTableWithFreeList.Element<String> element = new HashTableWithFreeList.Element<>(13, "thirteen");
        int expectedPosition = 5;
        int expectedFreeListHeadPosition = 0;
        int freeListPosition = 3;
        HashTableWithFreeList.Element<String> noneligibleElement = hashTableWithFreeList.at(expectedPosition).element;

        int actualPosition = Chapter11.inPlaceChainedHashInsert(hashTableWithFreeList, element);

        assertEquals(expectedPosition, actualPosition);
        assertEquals(element, hashTableWithFreeList.at(expectedPosition).element);
        assertEquals(noneligibleElement, hashTableWithFreeList.at(freeListPosition).element);
        assertEquals(expectedFreeListHeadPosition, hashTableWithFreeList.F);
    }

    @Test
    public void shouldInsertOntoTakenPositionWithNoneligibleElementInHashTableWithFreeList2() {
        HashTableWithFreeList<String> hashTableWithFreeList = getExemplaryHashTableWithFreeList();
        HashTableWithFreeList.Element<String> element = new HashTableWithFreeList.Element<>(20, "twenty");
        int expectedPosition = 4;
        int expectedFreeListHeadPosition = 0;
        int freeListPosition = 3;
        HashTableWithFreeList.Element<String> noneligibleElement = hashTableWithFreeList.at(expectedPosition).element;

        int actualPosition = Chapter11.inPlaceChainedHashInsert(hashTableWithFreeList, element);

        assertEquals(expectedPosition, actualPosition);
        assertEquals(element, hashTableWithFreeList.at(expectedPosition).element);
        assertEquals(noneligibleElement, hashTableWithFreeList.at(freeListPosition).element);
        assertEquals(expectedFreeListHeadPosition, hashTableWithFreeList.F);
    }

    @Test
    public void shouldThrowExceptionWhenInsertingIntoFullHashTableWithFreeList() {
        HashTableWithFreeList<String> hashTableWithFreeList =
                HashTableWithFreeList.withLengthAndHashFunction(8, key -> key % 8);
        hashTableWithFreeList.F = -1;
        HashTableWithFreeList.Element<String> element = new HashTableWithFreeList.Element<>(25, "twentyFive");

        thrown.expect(IllegalStateException.class);
        thrown.expectMessage("overflow");
        Chapter11.inPlaceChainedHashInsert(hashTableWithFreeList, element);
    }

    @Test
    public void shouldFindElementInHashTableWithFreeList() {
        HashTableWithFreeList<String> hashTableWithFreeList = getExemplaryHashTableWithFreeList();
        int key = 9;
        int expectedPosition = 5;

        Integer actualPosition = Chapter11.inPlaceChainedHashSearch(hashTableWithFreeList, key);

        assertEquals(Integer.valueOf(expectedPosition), actualPosition);
    }

    @Test
    public void shouldNotFindNonexistentElementInHashTableWithFreeList() {
        HashTableWithFreeList<String> hashTableWithFreeList = getExemplaryHashTableWithFreeList();
        int key = 33;

        Integer actualPosition = Chapter11.inPlaceChainedHashSearch(hashTableWithFreeList, key);

        assertNull(actualPosition);
    }

    @Test
    public void shouldDeleteFromHashTableWithFreeList() {
        HashTableWithFreeList<String> hashTableWithFreeList = getExemplaryHashTableWithFreeList();
        int elementPosition = 5;
        HashTableWithFreeList.Element<String> element = hashTableWithFreeList.at(elementPosition).element;
        int freeListPosition = hashTableWithFreeList.F;

        Chapter11.inPlaceChainedHashDelete(hashTableWithFreeList, element);

        assertEquals(-1, hashTableWithFreeList.at(elementPosition).prev);
        assertEquals(elementPosition, hashTableWithFreeList.F);
        assertEquals(freeListPosition, hashTableWithFreeList.at(elementPosition).next);
    }

    @Test
    public void shouldInsertIntoHashTableWithProbing() {
        HashTableWithOpenAddressing hashTableWithOpenAddressing =
                HashTableWithOpenAddressing.withLengthAndHashFunction(5, (key, i) -> (key % 5 + i) % 5);
        hashTableWithOpenAddressing.set(new ZeroBasedIndexedArray<>(35, 51, null, 38, null));
        int key = 45;

        int actualPosition = Chapter11.hashInsert(hashTableWithOpenAddressing, key);

        assertEquals(2, actualPosition);
        assertEquals(Integer.valueOf(key), hashTableWithOpenAddressing.at(2));
    }

    @Test
    public void shouldThrowExceptionWhenInsertingIntoFullHashTableWithProbing() {
        HashTableWithOpenAddressing hashTableWithOpenAddressing =
                HashTableWithOpenAddressing.withLengthAndHashFunction(5, (key, i) -> (key % 5 + i) % 5);
        hashTableWithOpenAddressing.set(new ZeroBasedIndexedArray<>(35, 51, 45, 38, 16));
        int key = 32;

        thrown.expect(IllegalStateException.class);
        thrown.expectMessage("hash table overflow");
        Chapter11.hashInsert(hashTableWithOpenAddressing, key);
    }

    @Test
    public void shouldFindElementInHashTableWithProbing() {
        HashTableWithOpenAddressing hashTableWithOpenAddressing =
                HashTableWithOpenAddressing.withLengthAndHashFunction(5, (key, i) -> (key % 5 + i) % 5);
        hashTableWithOpenAddressing.set(new ZeroBasedIndexedArray<>(35, 51, 45, 38, null));
        int key = 45;

        Integer actualPosition = Chapter11.hashSearch(hashTableWithOpenAddressing, key);

        assertEquals(Integer.valueOf(2), actualPosition);
    }

    @Test
    public void shouldNotFindNonexistentElementInHashTableWithProbing() {
        HashTableWithOpenAddressing hashTableWithOpenAddressing =
                HashTableWithOpenAddressing.withLengthAndHashFunction(5, (key, i) -> (key % 5 + i) % 5);
        hashTableWithOpenAddressing.set(new ZeroBasedIndexedArray<>(35, 51, 45, 38, null));
        int key = 26;

        Integer actualPosition = Chapter11.hashSearch(hashTableWithOpenAddressing, key);

        assertNull(actualPosition);
    }

    @Test
    public void shouldDeleteFromHashTableWithProbing() {
        HashTableWithOpenAddressing hashTableWithOpenAddressing =
                HashTableWithOpenAddressing.withLengthAndHashFunction(5, (key, i) -> (key % 5 + i) % 5);
        hashTableWithOpenAddressing.set(new ZeroBasedIndexedArray<>(35, 51, 45, 38, null));
        int key = 45;

        Chapter11.hashDelete(hashTableWithOpenAddressing, key);

        assertEquals(DELETED, hashTableWithOpenAddressing.at(2));
    }

    @Test
    public void shouldNotDeleteNonexistentElementFromHashTableWithProbing() {
        HashTableWithOpenAddressing hashTableWithOpenAddressing =
                HashTableWithOpenAddressing.withLengthAndHashFunction(5, (key, i) -> (key % 5 + i) % 5);
        hashTableWithOpenAddressing.set(new ZeroBasedIndexedArray<>(35, 51, 45, 38, 3));
        int key = 23;

        Chapter11.hashDelete(hashTableWithOpenAddressing, key);

        for (int i = 0; i <= hashTableWithOpenAddressing.length - 1; i++) {
            assertNotEquals(DELETED, hashTableWithOpenAddressing.at(i));
        }
    }

    @Test
    public void shouldInsertIntoHashTableWithProbingUsingHashInsert_() {
        HashTableWithOpenAddressing hashTableWithOpenAddressing =
                HashTableWithOpenAddressing.withLengthAndHashFunction(5, (key, i) -> (key % 5 + i) % 5);
        hashTableWithOpenAddressing.set(new ZeroBasedIndexedArray<>(35, 51, DELETED, 38, DELETED));
        int key = 45;

        int actualPosition = Chapter11.hashInsert_(hashTableWithOpenAddressing, key);

        assertEquals(2, actualPosition);
        assertEquals(Integer.valueOf(key), hashTableWithOpenAddressing.at(2));
    }

    @Test
    public void shouldThrowExceptionWhenInsertingIntoFullHashTableWithProbingUsingHashInsert_() {
        HashTableWithOpenAddressing hashTableWithOpenAddressing =
                HashTableWithOpenAddressing.withLengthAndHashFunction(5, (key, i) -> (key % 5 + i) % 5);
        hashTableWithOpenAddressing.set(new ZeroBasedIndexedArray<>(35, 51, 45, 38, 16));
        int key = 32;

        thrown.expect(IllegalStateException.class);
        thrown.expectMessage("hash table overflow");
        Chapter11.hashInsert_(hashTableWithOpenAddressing, key);
    }

    @Test
    public void shouldFindElementUsingQuadraticProbing() {
        ZeroBasedIndexedArray<Integer> hashTable = new ZeroBasedIndexedArray<>(24, null, 34, 35, 51, null, 27, null);
        HashFunction h = key -> key % hashTable.length;
        int key = 27;

        Integer actualPosition = Chapter11.quadraticProbingSearch(hashTable, key, h);

        assertEquals(Integer.valueOf(6), actualPosition);
    }

    @Test
    public void shouldNotFindNonexistentElementUsingQuadraticProbing() {
        ZeroBasedIndexedArray<Integer> hashTable = new ZeroBasedIndexedArray<>(24, null, 34, 35, 51, null, 27, null);
        HashFunction h = key -> key % hashTable.length;
        int key = 43;

        Integer actualPosition = Chapter11.quadraticProbingSearch(hashTable, key, h);

        assertNull(actualPosition);
    }

    @Test
    public void shouldNotFindNonexistentElementInFullHashTableUsingQuadraticProbing() {
        ZeroBasedIndexedArray<Integer> hashTable = new ZeroBasedIndexedArray<>(24, 9, 34, 35, 51, 13, 27, 20);
        HashFunction h = key -> key % hashTable.length;
        int key = 43;

        Integer actualPosition = Chapter11.quadraticProbingSearch(hashTable, key, h);

        assertNull(actualPosition);
    }

}
