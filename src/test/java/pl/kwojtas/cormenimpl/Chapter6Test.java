package pl.kwojtas.cormenimpl;

import org.junit.Test;
import pl.kwojtas.cormenimpl.util.Array;
import pl.kwojtas.cormenimpl.util.Heap;
import pl.kwojtas.cormenimpl.util.List;
import pl.kwojtas.cormenimpl.util.PriorityQueueWithRanks;
import pl.kwojtas.cormenimpl.util.PriorityQueueWithRanks.KeyWithRank;
import pl.kwojtas.cormenimpl.util.Young;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static pl.kwojtas.cormenimpl.TestUtil.assertArrayEquals;
import static pl.kwojtas.cormenimpl.TestUtil.assertShuffled;
import static pl.kwojtas.cormenimpl.TestUtil.assertSorted;
import static pl.kwojtas.cormenimpl.util.Util.ceil;

public class Chapter6Test {

    @Test
    public void shouldHavePrivateConstructor() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Constructor<Chapter6> constructor = Chapter6.class.getDeclaredConstructor();
        assertTrue(Modifier.isPrivate(constructor.getModifiers()));
        constructor.setAccessible(true);
        constructor.newInstance();
    }

    @Test
    public void shouldSortArrayUsingHeapsort() {
        // given
        Array<Integer> array = new Array<>(5, 7, 9, 2, 6, 8, 6, 6, 3, 1, 7, 8);
        Array<Integer> original = new Array<>(array);

        // when
        Chapter6.heapsort(array);

        // then
        assertShuffled(original, array);
        assertSorted(array);
    }

    @Test
    public void shouldRestoreMinHeapPropertyUsingMinHeapify() {
        // given
        Array<Integer> array = new Array<>(0, 1, 16, 3, 4, 7, 17, 12, 10, 5, 13, 9, 8, 27);
        Heap<Integer> heap = new Heap<>(array);
        Heap<Integer> original = new Heap<>(array);
        int position = 3;

        // when
        Chapter6.minHeapify(heap, position);

        // then
        assertShuffled(original, heap);
        assertEquals(original.heapSize, heap.heapSize);
        assertMinHeap(heap);
    }

    private void assertMinHeap(Heap<Integer> heap) {
        for (int i = 2; i <= heap.heapSize; i++) {
            assertTrue(heap.at(i / 2) <= heap.at(i));
        }
    }

    @Test
    public void shouldRestoreMaxHeapPropertyUsingIterativeMaxHeapify() {
        // given
        Array<Integer> array = new Array<>(27, 17, 3, 16, 13, 10, 1, 5, 7, 12, 4, 8, 9, 0);
        Heap<Integer> heap = new Heap<>(array);
        Heap<Integer> original = new Heap<>(array);
        int position = 3;

        // when
        Chapter6.iterativeMaxHeapify(heap, position);

        // then
        assertShuffled(original, heap);
        assertEquals(original.heapSize, heap.heapSize);
        assertMaxHeap(heap);
    }

    private void assertMaxHeap(Heap<Integer> heap) {
        for (int i = 2; i <= heap.heapSize; i++) {
            assertTrue(heap.at(i / 2) >= heap.at(i));
        }
    }

    @Test
    public void shouldGetMaximumFromMaxHeap() {
        // given
        Array<Integer> array = new Array<>(27, 7, 20, 4, 6, 13, 17, 0, 3, 2, 1, 5, 11, 10);
        Heap<Integer> heap = new Heap<>(array);
        Heap<Integer> original = new Heap<>(array);

        // when
        int actualMaximum = Chapter6.heapMaximum(heap);

        // then
        assertEquals(original.heapSize, heap.heapSize);
        assertArrayEquals(original, heap);
        assertEquals(original.at(1), Integer.valueOf(actualMaximum));
    }

    @Test
    public void shouldExtractMaximumFromMaxHeap() {
        // given
        Array<Integer> array = new Array<>(27, 7, 20, 4, 6, 13, 17, 0, 3, 2, 1, 5, 11, 10);
        Heap<Integer> heap = new Heap<>(array);
        Heap<Integer> original = new Heap<>(array);

        // when
        int actualMaximum = Chapter6.heapExtractMax(heap);

        // then
        assertEquals(original.heapSize - 1, heap.heapSize);
        assertMaxHeap(heap);
        assertEquals(original.at(1), Integer.valueOf(actualMaximum));
        for (int i = 2; i <= original.heapSize; i++) {
            assertHeapContains(heap, original.at(i));
        }
    }

    private void assertHeapContains(Heap<Integer> heap, int element) {
        boolean found = false;
        for (int i = 1; i <= heap.heapSize && !found; i++) {
            found = heap.at(i).equals(element);
        }
        assertTrue(found);
    }

    @Test(expected = RuntimeException.class)
    public void shouldThrowExceptionWhenExtractingMaximumFromEmptyHeap() {
        // given

        try {
            // when
            Chapter6.heapExtractMax(Heap.withLength(3));
        } catch (RuntimeException e) {
            // then
            assertEquals("heap underflow", e.getMessage());
            throw e;
        }
    }

    @Test
    public void shouldIncreaseKeyInMaxHeap() {
        // given
        Array<Integer> array = new Array<>(27, 7, 20, 4, 6, 13, 17, 0, 3, 2, 1, 5, 11, 10);
        Heap<Integer> heap = new Heap<>(array);
        Heap<Integer> original = new Heap<>(array);
        int position = 12;
        int newKey = 23;

        // when
        Chapter6.heapIncreaseKey(heap, position, newKey);

        // then
        assertEquals(original.heapSize, heap.heapSize);
        assertMaxHeap(heap);
        assertHeapContains(heap, newKey);
        for (int i = 1; i <= original.heapSize; i++) {
            if (i != position) {
                assertHeapContains(heap, original.at(i));
            }
        }
    }

    @Test(expected = RuntimeException.class)
    public void shouldThrowExceptionWhenIncreasingKeyInMaxHeapWithSmallerNumber() {
        // given
        Array<Integer> array = new Array<>(27, 7, 20, 4, 6, 13, 17, 0, 3, 2, 1, 5, 11, 10);
        Heap<Integer> heap = new Heap<>(array);
        int position = 3;
        int newKey = 16;

        try {
            // when
            Chapter6.heapIncreaseKey(heap, position, newKey);
        } catch (RuntimeException e) {
            // then
            assertEquals("new key is smaller than current key", e.getMessage());
            throw e;
        }
    }

    @Test
    public void shouldInsertIntoMaxHeap() {
        // given
        Array<Integer> array = new Array<>(27, 7, 20, 4, 6, 13, 17, 0, 3, 2, 1, 5, 11, 10);
        Heap<Integer> heap = new Heap<>(array, array.length + 1);
        Heap<Integer> original = new Heap<>(array, array.length + 1);
        int newKey = 34;

        // when
        Chapter6.maxHeapInsert(heap, newKey);

        // then
        assertEquals(original.heapSize + 1, heap.heapSize);
        assertMaxHeap(heap);
        assertHeapContains(heap, newKey);
        for (int i = 1; i <= original.heapSize; i++) {
            assertHeapContains(heap, original.at(i));
        }
    }

    @Test
    public void shouldGetMinimumFromMinHeap() {
        // given
        Array<Integer> array = new Array<>(0, 6, 1, 11, 7, 3, 2, 27, 13, 17, 20, 10, 4, 5);
        Heap<Integer> heap = new Heap<>(array);
        Heap<Integer> original = new Heap<>(array);

        // when
        int actualMinimum = Chapter6.heapMinimum(heap);

        // then
        assertEquals(original.heapSize, heap.heapSize);
        assertArrayEquals(original, heap);
        assertEquals(original.at(1), Integer.valueOf(actualMinimum));
    }

    @Test
    public void shouldExtractMinimumFromMinHeap() {
        // given
        Array<Integer> array = new Array<>(0, 6, 1, 11, 7, 3, 2, 27, 13, 17, 20, 10, 4, 5);
        Heap<Integer> heap = new Heap<>(array);
        Heap<Integer> original = new Heap<>(array);

        // when
        int actualMinimum = Chapter6.heapExtractMin(heap);

        // then
        assertEquals(original.heapSize - 1, heap.heapSize);
        assertMinHeap(heap);
        assertEquals(original.at(1), Integer.valueOf(actualMinimum));
        for (int i = 2; i <= original.heapSize; i++) {
            assertHeapContains(heap, original.at(i));
        }
    }

    @Test(expected = RuntimeException.class)
    public void shouldThrowExceptionWhenExtractingMinimumFromEmptyHeap() {
        // given

        try {
            // when
            Chapter6.heapExtractMin(Heap.withLength(3));
        } catch (RuntimeException e) {
            // then
            assertEquals("heap underflow", e.getMessage());
            throw e;
        }
    }

    @Test
    public void shouldDecreaseKeyInMinHeap() {
        // given
        Array<Integer> array = new Array<>(0, 6, 1, 11, 7, 3, 2, 27, 13, 17, 20, 10, 4, 5);
        Heap<Integer> heap = new Heap<>(array);
        Heap<Integer> original = new Heap<>(array);
        int position = 11;
        int newKey = 4;

        // when
        Chapter6.heapDecreaseKey(heap, position, newKey);

        // then
        assertEquals(original.heapSize, heap.heapSize);
        assertMinHeap(heap);
        assertHeapContains(heap, newKey);
        for (int i = 1; i <= original.heapSize; i++) {
            if (i != position) {
                assertHeapContains(heap, original.at(i));
            }
        }
    }

    @Test(expected = RuntimeException.class)
    public void shouldThrowExceptionWhenDecreasingKeyInMinHeapWithLargerNumber() {
        // given
        Array<Integer> array = new Array<>(0, 6, 1, 11, 7, 3, 2, 27, 13, 17, 20, 10, 4, 5);
        Heap<Integer> heap = new Heap<>(array);
        int position = 4;
        int newKey = 12;

        try {
            // when
            Chapter6.heapDecreaseKey(heap, position, newKey);
        } catch (RuntimeException e) {
            // then
            assertEquals("new key is larger than current key", e.getMessage());
            throw e;
        }
    }

    @Test
    public void shouldInsertIntoMinHeap() {
        // given
        Array<Integer> array = new Array<>(1, 6, 2, 11, 7, 3, 4, 27, 13, 17, 20, 10, 14, 5);
        Heap<Integer> heap = new Heap<>(array, array.length + 3);
        Heap<Integer> original = new Heap<>(array, array.length + 3);
        int newKey = 0;

        // when
        Chapter6.minHeapInsert(heap, newKey);

        // then
        assertEquals(original.heapSize + 1, heap.heapSize);
        assertMinHeap(heap);
        assertHeapContains(heap, newKey);
        for (int i = 1; i <= original.heapSize; i++) {
            assertHeapContains(heap, original.at(i));
        }
    }

    @Test
    public void shouldEnqueueUsingPriorityQueue() {
        // given
        PriorityQueueWithRanks<String> priorityQueueWithRanks = getExemplaryMinPriorityQueueWithRanks();
        String newKey = "jjj";
        int currentRank = 10;

        // when
        Chapter6.enqueueUsingPriorityQueue(priorityQueueWithRanks, newKey);

        // then
        assertEquals(currentRank + 1, priorityQueueWithRanks.getCurrentRank());
        assertEquals(newKey, priorityQueueWithRanks.at(currentRank).key);
        assertEquals(currentRank, priorityQueueWithRanks.at(currentRank).rank);
    }

    private PriorityQueueWithRanks<String> getExemplaryMinPriorityQueueWithRanks() {
        PriorityQueueWithRanks<String> priorityQueueWithRanks = new PriorityQueueWithRanks<>(12);
        priorityQueueWithRanks.set(1, new KeyWithRank<>("aaa", priorityQueueWithRanks.getCurrentRank()));
        priorityQueueWithRanks.set(3, new KeyWithRank<>("bbb", priorityQueueWithRanks.getCurrentRank()));
        priorityQueueWithRanks.set(2, new KeyWithRank<>("ccc", priorityQueueWithRanks.getCurrentRank()));
        priorityQueueWithRanks.set(6, new KeyWithRank<>("ddd", priorityQueueWithRanks.getCurrentRank()));
        priorityQueueWithRanks.set(4, new KeyWithRank<>("eee", priorityQueueWithRanks.getCurrentRank()));
        priorityQueueWithRanks.set(7, new KeyWithRank<>("fff", priorityQueueWithRanks.getCurrentRank()));
        priorityQueueWithRanks.set(8, new KeyWithRank<>("ggg", priorityQueueWithRanks.getCurrentRank()));
        priorityQueueWithRanks.set(9, new KeyWithRank<>("hhh", priorityQueueWithRanks.getCurrentRank()));
        priorityQueueWithRanks.set(5, new KeyWithRank<>("iii", priorityQueueWithRanks.getCurrentRank()));
        priorityQueueWithRanks.heapSize = 9;
        return priorityQueueWithRanks;
    }

    @Test
    public void shouldDequeueUsingPriorityQueue() {
        // given
        PriorityQueueWithRanks<String> priorityQueueWithRanks = getExemplaryMinPriorityQueueWithRanks();
        String expectedDeletedElement = "aaa";
        int currentRank = 10;

        // when
        String actualDeletedElement = Chapter6.dequeueUsingPriorityQueue(priorityQueueWithRanks);

        // then
        assertEquals(currentRank, priorityQueueWithRanks.getCurrentRank());
        assertEquals(expectedDeletedElement, actualDeletedElement);
    }

    @Test
    public void shouldPushUsingPriorityQueue() {
        // given
        PriorityQueueWithRanks<String> priorityQueueWithRanks = getExemplaryMaxPriorityQueueWithRanks();
        String newKey = "jjj";
        int currentRank = 10;

        // when
        Chapter6.pushUsingPriorityQueue(priorityQueueWithRanks, newKey);

        // then
        assertEquals(currentRank + 1, priorityQueueWithRanks.getCurrentRank());
        assertEquals(newKey, priorityQueueWithRanks.at(1).key);
        assertEquals(currentRank, priorityQueueWithRanks.at(1).rank);
    }

    private PriorityQueueWithRanks<String> getExemplaryMaxPriorityQueueWithRanks() {
        PriorityQueueWithRanks<String> priorityQueueWithRanks = new PriorityQueueWithRanks<>(12);
        priorityQueueWithRanks.set(7, new KeyWithRank<>("aaa", priorityQueueWithRanks.getCurrentRank()));
        priorityQueueWithRanks.set(4, new KeyWithRank<>("bbb", priorityQueueWithRanks.getCurrentRank()));
        priorityQueueWithRanks.set(9, new KeyWithRank<>("ccc", priorityQueueWithRanks.getCurrentRank()));
        priorityQueueWithRanks.set(8, new KeyWithRank<>("ddd", priorityQueueWithRanks.getCurrentRank()));
        priorityQueueWithRanks.set(5, new KeyWithRank<>("eee", priorityQueueWithRanks.getCurrentRank()));
        priorityQueueWithRanks.set(2, new KeyWithRank<>("fff", priorityQueueWithRanks.getCurrentRank()));
        priorityQueueWithRanks.set(6, new KeyWithRank<>("ggg", priorityQueueWithRanks.getCurrentRank()));
        priorityQueueWithRanks.set(3, new KeyWithRank<>("hhh", priorityQueueWithRanks.getCurrentRank()));
        priorityQueueWithRanks.set(1, new KeyWithRank<>("iii", priorityQueueWithRanks.getCurrentRank()));
        priorityQueueWithRanks.heapSize = 9;
        return priorityQueueWithRanks;
    }

    @Test
    public void shouldPopUsingPriorityQueue() {
        // given
        PriorityQueueWithRanks<String> priorityQueueWithRanks = getExemplaryMaxPriorityQueueWithRanks();
        String expectedDeletedElement = "iii";
        int currentRank = 10;

        // when
        String actualDeletedElement = Chapter6.popUsingPriorityQueue(priorityQueueWithRanks);

        // then
        assertEquals(currentRank, priorityQueueWithRanks.getCurrentRank());
        assertEquals(expectedDeletedElement, actualDeletedElement);
    }

    @Test
    public void shouldDeleteFromMaxHeap() {
        // given
        Array<Integer> array = new Array<>(27, 7, 20, 4, 6, 13, 17, 0, 3, 2, 1, 5, 11, 10);
        Heap<Integer> heap = new Heap<>(array);
        Heap<Integer> original = new Heap<>(array);
        int position = 10;

        // when
        Integer actualKey = Chapter6.maxHeapDelete(heap, position);

        // then
        assertEquals(original.heapSize - 1, heap.heapSize);
        assertMaxHeap(heap);
        assertHeapContains(original, actualKey);
        for (int i = 1; i <= original.heapSize; i++) {
            if (i != position) {
                assertHeapContains(heap, original.at(i));
            }
        }
    }

    @Test
    public void shouldMergeSortedLists() {
        // given
        Array<List<Integer>> sortedLists = new Array<>(
                new List<>(14, 20, 22, 45, 46),
                new List<>(4, 4, 23),
                new List<>(1),
                new List<>(5, 6, 12, 16, 18, 22, 24, 56, 67),
                new List<>(1),
                new List<>(4, 6, 20, 30),
                new List<>(),
                new List<>(68, 68, 68, 69),
                new List<>(45),
                new List<>(2, 34)
        );
        Array<List<Integer>> original = Array.withLength(sortedLists.length);
        for (int i = 1; i <= sortedLists.length; i++) {
            original.set(i, new List<>(sortedLists.at(i)));
        }

        // when
        List<Integer> actualMergedList = Chapter6.mergeSortedLists(sortedLists);

        // then
        assertSorted(actualMergedList.toArray());
        assertMerged(original, actualMergedList);
    }

    private void assertMerged(Array<List<Integer>> sortedLists, List<Integer> actualMergedList) {
        List.Node<Integer> x = actualMergedList.head;
        while (x != null) {
            boolean found = false;
            for (int i = 1; i <= sortedLists.length && !found; i++) {
                List.Node<Integer> y = sortedLists.at(i).head;
                while (y != null && !found) {
                    if (y.key.equals(x.key)) {
                        y.key = Integer.MAX_VALUE; // mark found element with a special value
                        found = true;
                    }
                    y = y.next;
                }
            }
            assertTrue(found);
            x = x.next;
        }
        for (int i = 1; i <= sortedLists.length; i++) {
            List.Node<Integer> y = sortedLists.at(i).head;
            while (y != null) {
                assertEquals(Integer.valueOf(Integer.MAX_VALUE), y.key);
                y = y.next;
            }
        }
    }

    @Test
    public void shouldBuildMaxHeapUsingBuildMaxHeap_() {
        // given
        Array<Integer> array = new Array<>(20, 4, 0, 13, 5, 17, 1, 2, 6, 10, 7, 27, 11, 3);
        Array<Integer> original = new Array<>(array);

        // when
        Heap<Integer> actualHeap = Chapter6.buildMaxHeap_(array);

        // then
        for (int i = 1; i <= original.length; i++) {
            assertHeapContains(actualHeap, original.at(i));
        }
        assertEquals(original.length, actualHeap.heapSize);
        assertMaxHeap(actualHeap);
    }

    @Test
    public void shouldExtractMaximumFromMultiaryMaxHeap() {
        // given
        Array<Integer> array = new Array<>(32, 17, 27, 13, 20, 16, 8, 9, 13, 18, 23, 24, 5, 3, 12, 7);
        Heap<Integer> heap = new Heap<>(array);
        Heap<Integer> original = new Heap<>(array);
        int degree = 4;

        // when
        int actualMaximum = Chapter6.multiaryHeapExtractMax(heap, degree);

        // then
        assertEquals(original.heapSize - 1, heap.heapSize);
        assertMultiaryMaxHeap(heap, degree);
        assertEquals(original.at(1), Integer.valueOf(actualMaximum));
        for (int i = 2; i <= original.heapSize; i++) {
            assertHeapContains(heap, original.at(i));
        }
    }

    private void assertMultiaryMaxHeap(Heap<Integer> heap, int degree) {
        for (int i = 2; i <= heap.heapSize; i++) {
            int parent = ceil(i - 1, degree);
            assertTrue(heap.at(parent) >= heap.at(i));
        }
    }

    @Test(expected = RuntimeException.class)
    public void shouldThrowExceptionWhenExtractingMaximumFromEmptyMultiaryHeap() {
        // given
        int degree = 4;

        try {
            // when
            Chapter6.multiaryHeapExtractMax(Heap.withLength(6), degree);
        } catch (RuntimeException e) {
            // then
            assertEquals("heap underflow", e.getMessage());
            throw e;
        }
    }

    @Test
    public void shouldInsertToMultiaryMaxHeap() {
        // given
        Array<Integer> array = new Array<>(32, 17, 27, 13, 20, 16, 8, 9, 13, 18, 23, 24, 5, 3, 12, 7);
        Heap<Integer> heap = new Heap<>(array, array.length + 1);
        Heap<Integer> original = new Heap<>(array, array.length + 1);
        int degree = 4;
        int newKey = 35;

        // when
        Chapter6.multiaryMaxHeapInsert(heap, degree, newKey);

        // then
        assertEquals(original.heapSize + 1, heap.heapSize);
        assertMultiaryMaxHeap(heap, degree);
        assertHeapContains(heap, newKey);
        for (int i = 1; i <= original.heapSize; i++) {
            assertHeapContains(heap, original.at(i));
        }
    }

    @Test
    public void shouldIncreaseKeyInMultiaryMaxHeap() {
        // given
        Array<Integer> array = new Array<>(32, 17, 27, 13, 20, 16, 8, 9, 13, 18, 23, 24, 5, 3, 12, 7);
        Heap<Integer> heap = new Heap<>(array);
        Heap<Integer> original = new Heap<>(array);
        int degree = 4;
        int position = 14;
        int newKey = 35;

        // when
        Chapter6.multiaryHeapIncreaseKey(heap, degree, position, newKey);

        // then
        assertEquals(original.heapSize, heap.heapSize);
        assertMultiaryMaxHeap(heap, degree);
        assertHeapContains(heap, newKey);
        for (int i = 1; i <= original.heapSize; i++) {
            if (i != position) {
                assertHeapContains(heap, original.at(i));
            }
        }
    }

    @Test
    public void shouldSortArrayUsingYoungSort() {
        // given
        Array<Integer> array = new Array<>(5, 7, 9, 2, 6, 8, 6, 6, 3, 1, 7, 8, 6, 3, 7, 8);
        Array<Integer> original = new Array<>(array);

        // when
        Chapter6.youngSort(array);

        // then
        assertShuffled(original, array);
        assertSorted(array);
    }

    @Test
    public void shouldFindKeyUsingYoungSearch() {
        // given
        Young Y = new Young(
                new Array<>(2, 3, 14, 16),
                new Array<>(4, 8, Integer.MAX_VALUE, Integer.MAX_VALUE),
                new Array<>(5, 12, Integer.MAX_VALUE, Integer.MAX_VALUE),
                new Array<>(9, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE)
        );
        int key = 12;

        // when
        boolean keyFound = Chapter6.youngSearch(Y, Y.rows, Y.columns, key);

        // then
        assertTrue(keyFound);
    }

    @Test
    public void shouldNotFindNonexistentKeyUsingYoungSearch() {
        // given
        Young Y = new Young(
                new Array<>(2, 3, 14, 16),
                new Array<>(4, 8, Integer.MAX_VALUE, Integer.MAX_VALUE),
                new Array<>(5, 12, Integer.MAX_VALUE, Integer.MAX_VALUE),
                new Array<>(9, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE)
        );
        int key = 10;

        // when
        boolean keyFound = Chapter6.youngSearch(Y, Y.rows, Y.columns, key);

        // then
        assertFalse(keyFound);
    }

}
