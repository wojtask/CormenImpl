package pl.kwojtas.cormenimpl;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import pl.kwojtas.cormenimpl.datastructure.Array;
import pl.kwojtas.cormenimpl.datastructure.Heap;
import pl.kwojtas.cormenimpl.datastructure.PriorityQueueWithRanks;
import pl.kwojtas.cormenimpl.datastructure.PriorityQueueWithRanks.KeyWithRank;
import pl.kwojtas.cormenimpl.datastructure.SinglyLinkedList;
import pl.kwojtas.cormenimpl.datastructure.Young;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static pl.kwojtas.cormenimpl.TestUtil.assertArrayEquals;
import static pl.kwojtas.cormenimpl.TestUtil.assertShuffled;
import static pl.kwojtas.cormenimpl.TestUtil.assertSorted;
import static pl.kwojtas.cormenimpl.Fundamental.ceil;

public class Chapter6Test {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void shouldHavePrivateConstructor() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Constructor<Chapter6> constructor = Chapter6.class.getDeclaredConstructor();
        assertTrue(Modifier.isPrivate(constructor.getModifiers()));
        constructor.setAccessible(true);
        constructor.newInstance();
    }

    @Test
    public void shouldSortArrayUsingHeapsort() {
        Array<Integer> array = Array.of(5, 7, 9, 2, 6, 8, 6, 6, 3, 1, 7, 8);
        Array<Integer> original = Array.copyOf(array);

        Chapter6.heapsort(array);

        assertShuffled(original, array);
        assertSorted(array);
    }

    @Test
    public void shouldRestoreMinHeapPropertyUsingMinHeapify() {
        Array<Integer> array = Array.of(0, 1, 16, 3, 4, 7, 17, 12, 10, 5, 13, 9, 8, 27);
        Heap<Integer> heap = new Heap<>(array);
        Heap<Integer> original = new Heap<>(array);
        int position = 3;

        Chapter6.minHeapify(heap, position);

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
        Array<Integer> array = Array.of(27, 17, 3, 16, 13, 10, 1, 5, 7, 12, 4, 8, 9, 0);
        Heap<Integer> heap = new Heap<>(array);
        Heap<Integer> original = new Heap<>(array);
        int position = 3;

        Chapter6.iterativeMaxHeapify(heap, position);

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
        Array<Integer> array = Array.of(27, 7, 20, 4, 6, 13, 17, 0, 3, 2, 1, 5, 11, 10);
        Heap<Integer> heap = new Heap<>(array);
        Heap<Integer> original = new Heap<>(array);

        int actualMaximum = Chapter6.heapMaximum(heap);

        assertEquals(original.heapSize, heap.heapSize);
        assertArrayEquals(original, heap);
        assertEquals(original.at(1), Integer.valueOf(actualMaximum));
    }

    @Test
    public void shouldExtractMaximumFromMaxHeap() {
        Array<Integer> array = Array.of(27, 7, 20, 4, 6, 13, 17, 0, 3, 2, 1, 5, 11, 10);
        Heap<Integer> heap = new Heap<>(array);
        Heap<Integer> original = new Heap<>(array);

        int actualMaximum = Chapter6.heapExtractMax(heap);

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

    @Test
    public void shouldThrowExceptionWhenExtractingMaximumFromEmptyHeap() {
        thrown.expect(IllegalStateException.class);
        thrown.expectMessage("heap underflow");
        Chapter6.heapExtractMax(Heap.withLength(3));
    }

    @Test
    public void shouldIncreaseKeyInMaxHeap() {
        Array<Integer> array = Array.of(27, 7, 20, 4, 6, 13, 17, 0, 3, 2, 1, 5, 11, 10);
        Heap<Integer> heap = new Heap<>(array);
        Heap<Integer> original = new Heap<>(array);
        int position = 12;
        int newKey = 23;

        Chapter6.heapIncreaseKey(heap, position, newKey);

        assertEquals(original.heapSize, heap.heapSize);
        assertMaxHeap(heap);
        assertHeapContains(heap, newKey);
        for (int i = 1; i <= original.heapSize; i++) {
            if (i != position) {
                assertHeapContains(heap, original.at(i));
            }
        }
    }

    @Test
    public void shouldThrowExceptionWhenIncreasingKeyInMaxHeapWithSmallerNumber() {
        Array<Integer> array = Array.of(27, 7, 20, 4, 6, 13, 17, 0, 3, 2, 1, 5, 11, 10);
        Heap<Integer> heap = new Heap<>(array);
        int position = 3;
        int newKey = 16;

        thrown.expect(IllegalStateException.class);
        thrown.expectMessage("new key is smaller than current key");
        Chapter6.heapIncreaseKey(heap, position, newKey);
    }

    @Test
    public void shouldInsertIntoMaxHeap() {
        Array<Integer> array = Array.of(27, 7, 20, 4, 6, 13, 17, 0, 3, 2, 1, 5, 11, 10);
        Heap<Integer> heap = new Heap<>(array, array.length + 1);
        Heap<Integer> original = new Heap<>(array, array.length + 1);
        int newKey = 34;

        Chapter6.maxHeapInsert(heap, newKey);

        assertEquals(original.heapSize + 1, heap.heapSize);
        assertMaxHeap(heap);
        assertHeapContains(heap, newKey);
        for (int i = 1; i <= original.heapSize; i++) {
            assertHeapContains(heap, original.at(i));
        }
    }

    @Test
    public void shouldGetMinimumFromMinHeap() {
        Array<Integer> array = Array.of(0, 6, 1, 11, 7, 3, 2, 27, 13, 17, 20, 10, 4, 5);
        Heap<Integer> heap = new Heap<>(array);
        Heap<Integer> original = new Heap<>(array);

        int actualMinimum = Chapter6.heapMinimum(heap);

        assertEquals(original.heapSize, heap.heapSize);
        assertArrayEquals(original, heap);
        assertEquals(original.at(1), Integer.valueOf(actualMinimum));
    }

    @Test
    public void shouldExtractMinimumFromMinHeap() {
        Array<Integer> array = Array.of(0, 6, 1, 11, 7, 3, 2, 27, 13, 17, 20, 10, 4, 5);
        Heap<Integer> heap = new Heap<>(array);
        Heap<Integer> original = new Heap<>(array);

        int actualMinimum = Chapter6.heapExtractMin(heap);

        assertEquals(original.heapSize - 1, heap.heapSize);
        assertMinHeap(heap);
        assertEquals(original.at(1), Integer.valueOf(actualMinimum));
        for (int i = 2; i <= original.heapSize; i++) {
            assertHeapContains(heap, original.at(i));
        }
    }

    @Test
    public void shouldThrowExceptionWhenExtractingMinimumFromEmptyHeap() {
        thrown.expect(IllegalStateException.class);
        thrown.expectMessage("heap underflow");
        Chapter6.heapExtractMin(Heap.withLength(3));
    }

    @Test
    public void shouldDecreaseKeyInMinHeap() {
        Array<Integer> array = Array.of(0, 6, 1, 11, 7, 3, 2, 27, 13, 17, 20, 10, 4, 5);
        Heap<Integer> heap = new Heap<>(array);
        Heap<Integer> original = new Heap<>(array);
        int position = 11;
        int newKey = 4;

        Chapter6.heapDecreaseKey(heap, position, newKey);

        assertEquals(original.heapSize, heap.heapSize);
        assertMinHeap(heap);
        assertHeapContains(heap, newKey);
        for (int i = 1; i <= original.heapSize; i++) {
            if (i != position) {
                assertHeapContains(heap, original.at(i));
            }
        }
    }

    @Test
    public void shouldThrowExceptionWhenDecreasingKeyInMinHeapWithLargerNumber() {
        Array<Integer> array = Array.of(0, 6, 1, 11, 7, 3, 2, 27, 13, 17, 20, 10, 4, 5);
        Heap<Integer> heap = new Heap<>(array);
        int position = 4;
        int newKey = 12;

        thrown.expect(IllegalStateException.class);
        thrown.expectMessage("new key is larger than current key");
        Chapter6.heapDecreaseKey(heap, position, newKey);
    }

    @Test
    public void shouldInsertIntoMinHeap() {
        Array<Integer> array = Array.of(1, 6, 2, 11, 7, 3, 4, 27, 13, 17, 20, 10, 14, 5);
        Heap<Integer> heap = new Heap<>(array, array.length + 3);
        Heap<Integer> original = new Heap<>(array, array.length + 3);
        int newKey = 0;

        Chapter6.minHeapInsert(heap, newKey);

        assertEquals(original.heapSize + 1, heap.heapSize);
        assertMinHeap(heap);
        assertHeapContains(heap, newKey);
        for (int i = 1; i <= original.heapSize; i++) {
            assertHeapContains(heap, original.at(i));
        }
    }

    @Test
    public void shouldEnqueueUsingPriorityQueue() {
        PriorityQueueWithRanks<String> priorityQueueWithRanks = getExemplaryMinPriorityQueueWithRanks();
        String newKey = "jjj";
        int currentRank = 10;

        Chapter6.enqueueUsingPriorityQueue(priorityQueueWithRanks, newKey);

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
        PriorityQueueWithRanks<String> priorityQueueWithRanks = getExemplaryMinPriorityQueueWithRanks();
        String expectedDeletedElement = "aaa";
        int currentRank = 10;

        String actualDeletedElement = Chapter6.dequeueUsingPriorityQueue(priorityQueueWithRanks);

        assertEquals(currentRank, priorityQueueWithRanks.getCurrentRank());
        assertEquals(expectedDeletedElement, actualDeletedElement);
    }

    @Test
    public void shouldPushUsingPriorityQueue() {
        PriorityQueueWithRanks<String> priorityQueueWithRanks = getExemplaryMaxPriorityQueueWithRanks();
        String newKey = "jjj";
        int currentRank = 10;

        Chapter6.pushUsingPriorityQueue(priorityQueueWithRanks, newKey);

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
        PriorityQueueWithRanks<String> priorityQueueWithRanks = getExemplaryMaxPriorityQueueWithRanks();
        String expectedDeletedElement = "iii";
        int currentRank = 10;

        String actualDeletedElement = Chapter6.popUsingPriorityQueue(priorityQueueWithRanks);

        assertEquals(currentRank, priorityQueueWithRanks.getCurrentRank());
        assertEquals(expectedDeletedElement, actualDeletedElement);
    }

    @Test
    public void shouldDeleteFromMaxHeap() {
        Array<Integer> array = Array.of(27, 7, 20, 4, 6, 13, 17, 0, 3, 2, 1, 5, 11, 10);
        Heap<Integer> heap = new Heap<>(array);
        Heap<Integer> original = new Heap<>(array);
        int position = 10;

        Integer actualKey = Chapter6.maxHeapDelete(heap, position);

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
        Array<SinglyLinkedList<Integer>> sortedLists = Array.of(
                new SinglyLinkedList<>(14, 20, 22, 45, 46),
                new SinglyLinkedList<>(4, 4, 23),
                new SinglyLinkedList<>(1),
                new SinglyLinkedList<>(5, 6, 12, 16, 18, 22, 24, 56, 67),
                new SinglyLinkedList<>(1),
                new SinglyLinkedList<>(4, 6, 20, 30),
                new SinglyLinkedList<>(),
                new SinglyLinkedList<>(68, 68, 68, 69),
                new SinglyLinkedList<>(45),
                new SinglyLinkedList<>(2, 34)
        );
        Array<SinglyLinkedList<Integer>> original = Array.withLength(sortedLists.length);
        for (int i = 1; i <= sortedLists.length; i++) {
            original.set(i, new SinglyLinkedList<>(sortedLists.at(i)));
        }

        SinglyLinkedList<Integer> actualMergedList = Chapter6.mergeSortedLists(sortedLists);

        assertSorted(actualMergedList.toArray());
        assertMerged(original, actualMergedList);
    }

    private void assertMerged(Array<SinglyLinkedList<Integer>> sortedLists, SinglyLinkedList<Integer> actualMergedList) {
        SinglyLinkedList.Node<Integer> x = actualMergedList.head;
        while (x != null) {
            boolean found = false;
            for (int i = 1; i <= sortedLists.length && !found; i++) {
                SinglyLinkedList.Node<Integer> y = sortedLists.at(i).head;
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
            SinglyLinkedList.Node<Integer> y = sortedLists.at(i).head;
            while (y != null) {
                assertEquals(Integer.valueOf(Integer.MAX_VALUE), y.key);
                y = y.next;
            }
        }
    }

    @Test
    public void shouldBuildMaxHeapUsingBuildMaxHeap_() {
        Array<Integer> array = Array.of(20, 4, 0, 13, 5, 17, 1, 2, 6, 10, 7, 27, 11, 3);
        Array<Integer> original = Array.copyOf(array);

        Heap<Integer> actualHeap = Chapter6.buildMaxHeap_(array);

        for (int i = 1; i <= original.length; i++) {
            assertHeapContains(actualHeap, original.at(i));
        }
        assertEquals(original.length, actualHeap.heapSize);
        assertMaxHeap(actualHeap);
    }

    @Test
    public void shouldExtractMaximumFromMultiaryMaxHeap() {
        Array<Integer> array = Array.of(32, 17, 27, 13, 20, 16, 8, 9, 13, 18, 23, 24, 5, 3, 12, 7);
        Heap<Integer> heap = new Heap<>(array);
        Heap<Integer> original = new Heap<>(array);
        int degree = 4;

        int actualMaximum = Chapter6.multiaryHeapExtractMax(heap, degree);

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

    @Test
    public void shouldThrowExceptionWhenExtractingMaximumFromEmptyMultiaryHeap() {
        int degree = 4;

        thrown.expect(IllegalStateException.class);
        thrown.expectMessage("heap underflow");
        Chapter6.multiaryHeapExtractMax(Heap.withLength(6), degree);
    }

    @Test
    public void shouldInsertToMultiaryMaxHeap() {
        Array<Integer> array = Array.of(32, 17, 27, 13, 20, 16, 8, 9, 13, 18, 23, 24, 5, 3, 12, 7);
        Heap<Integer> heap = new Heap<>(array, array.length + 1);
        Heap<Integer> original = new Heap<>(array, array.length + 1);
        int degree = 4;
        int newKey = 35;

        Chapter6.multiaryMaxHeapInsert(heap, degree, newKey);

        assertEquals(original.heapSize + 1, heap.heapSize);
        assertMultiaryMaxHeap(heap, degree);
        assertHeapContains(heap, newKey);
        for (int i = 1; i <= original.heapSize; i++) {
            assertHeapContains(heap, original.at(i));
        }
    }

    @Test
    public void shouldIncreaseKeyInMultiaryMaxHeap() {
        Array<Integer> array = Array.of(32, 17, 27, 13, 20, 16, 8, 9, 13, 18, 23, 24, 5, 3, 12, 7);
        Heap<Integer> heap = new Heap<>(array);
        Heap<Integer> original = new Heap<>(array);
        int degree = 4;
        int position = 14;
        int newKey = 35;

        Chapter6.multiaryHeapIncreaseKey(heap, degree, position, newKey);

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
        Array<Integer> array = Array.of(5, 7, 9, 2, 6, 8, 6, 6, 3, 1, 7, 8, 6, 3, 7, 8);
        Array<Integer> original = Array.copyOf(array);

        Chapter6.youngSort(array);

        assertShuffled(original, array);
        assertSorted(array);
    }

    @Test
    public void shouldFindKeyUsingYoungSearch() {
        Young Y = new Young(
                Array.of(2, 3, 14, 16),
                Array.of(4, 8, Integer.MAX_VALUE, Integer.MAX_VALUE),
                Array.of(5, 12, Integer.MAX_VALUE, Integer.MAX_VALUE),
                Array.of(9, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE)
        );
        int key = 12;

        boolean keyFound = Chapter6.youngSearch(Y, Y.rows, Y.columns, key);

        assertTrue(keyFound);
    }

    @Test
    public void shouldNotFindNonexistentKeyUsingYoungSearch() {
        Young Y = new Young(
                Array.of(2, 3, 14, 16),
                Array.of(4, 8, Integer.MAX_VALUE, Integer.MAX_VALUE),
                Array.of(5, 12, Integer.MAX_VALUE, Integer.MAX_VALUE),
                Array.of(9, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE)
        );
        int key = 10;

        boolean keyFound = Chapter6.youngSearch(Y, Y.rows, Y.columns, key);

        assertFalse(keyFound);
    }

}
