package pl.kwojtas.cormenimpl;

import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.junit.Test;
import org.junit.runner.RunWith;
import pl.kwojtas.cormenimpl.util.Array;
import pl.kwojtas.cormenimpl.util.Heap;
import pl.kwojtas.cormenimpl.util.Young;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static pl.kwojtas.cormenimpl.Chapter6.dAryParent;
import static pl.kwojtas.cormenimpl.Chapter6.parent;
import static pl.kwojtas.cormenimpl.TestUtil.assertArrayEquals;
import static pl.kwojtas.cormenimpl.TestUtil.assertShuffled;
import static pl.kwojtas.cormenimpl.TestUtil.assertSorted;
import static pl.kwojtas.cormenimpl.util.Util.geq;
import static pl.kwojtas.cormenimpl.util.Util.leq;

@RunWith(DataProviderRunner.class)
public class Chapter6Test {

    @DataProvider
    public static Object[][] provideDataForHeapsort() {
        return new Object[][]{
                {new Array<>(34)},
                {new Array<>(3,2,1)},
                {new Array<>(5,7,9,2,6,8,6,6,3,1,7,8)},
                {new Array<>(1,2,3,5,6,6,6,7,7,8,8,9)},
                {new Array<>(3.14,-2.75,-0.53,2.55,2.23)},
                {new Array<>("aaa","eee","ccc","ddd","bbb")}
        };
    }

    @Test
    @UseDataProvider("provideDataForHeapsort")
    public <T extends Comparable> void shouldSortArrayUsingHeapsort(Array<T> array) {
        // given
        Array<T> original = new Array<>(array);

        // when
        Chapter6.heapsort(array);

        // then
        assertShuffled(original, array);
        assertSorted(array);
    }

    @DataProvider
    public static Object[][] provideDataForMinHeapify() {
        return new Object[][]{
                {new Array<>(34), 1},
                {new Array<>(2,1,3), 1},
                {new Array<>(1,2,3), 1},
                {new Array<>(0,1,16,3,4,7,17,12,10,5,13,9,8,27), 3},
                {new Array<>(1,9,3,2,8,4,10,14,7,16), 2},
                {new Array<>(-2.75,3.14,2.55,-0.53,2.23), 2},
                {new Array<>("eee","aaa","ccc","bbb","ddd"), 1}
        };
    }

    @Test
    @UseDataProvider("provideDataForMinHeapify")
    public <T extends Comparable> void shouldRestoreMinHeapPropertyUsingMinHeapify(Array<T> array, int position) {
        // given
        Heap<T> heap = new Heap<>(array);
        Heap<T> original = new Heap<>(array);

        // when
        Chapter6.minHeapify(heap, position);

        // then
        assertShuffled(original, heap);
        assertEquals(original.heapSize, heap.heapSize);
        assertMinHeap(heap);
    }

    private <T extends Comparable> void assertMinHeap(Heap<T> heap) {
        for (int i = 2; i <= heap.heapSize; i++) {
            assertTrue(leq(heap.at(parent(i)), heap.at(i)));
        }
    }

    @DataProvider
    public static Object[][] provideDataForMaxHeapify() {
        return new Object[][]{
                {new Array<>(34), 1},
                {new Array<>(2,3,1), 1},
                {new Array<>(3,2,1), 1},
                {new Array<>(16,4,10,14,7,9,3,2,8,1), 2},
                {new Array<>(27,17,3,16,13,10,1,5,7,12,4,8,9,0), 3},
                {new Array<>(3.14,-2.75,-0.53,2.55,2.23), 2},
                {new Array<>("aaa","eee","ccc","ddd","bbb"), 1}
        };
    }

    @Test
    @UseDataProvider("provideDataForMaxHeapify")
    public <T extends Comparable> void shouldRestoreMaxHeapPropertyUsingIterativeMaxHeapify(Array<T> array, int position) {
        // given
        Heap<T> heap = new Heap<>(array);
        Heap<T> original = new Heap<>(array);

        // when
        Chapter6.iterativeMaxHeapify(heap, position);

        // then
        assertShuffled(original, heap);
        assertEquals(original.heapSize, heap.heapSize);
        assertMaxHeap(heap);
    }

    private <T extends Comparable> void assertMaxHeap(Heap<T> heap) {
        for (int i = 2; i <= heap.heapSize; i++) {
            assertTrue(geq(heap.at(parent(i)), heap.at(i)));
        }
    }

    @DataProvider
    public static Object[][] provideDataForHeapMaximum() {
        return new Object[][]{
                {new Array<>(34)},
                {new Array<>(3,2,1)},
                {new Array<>(3,1,2)},
                {new Array<>(16,14,10,8,7,9,3,2,4,1)},
                {new Array<>(27,7,20,4,6,13,17,0,3,2,1,5,11,10)}
        };
    }

    @Test
    @UseDataProvider("provideDataForHeapMaximum")
    public void shouldGetMaximumFromMaxHeap(Array<Integer> array) {
        // given
        Heap<Integer> heap = new Heap<>(array);
        Heap<Integer> original = new Heap<>(array);

        // when
        int actualMaximum = Chapter6.heapMaximum(heap);

        // then
        assertHeapEquals(original, heap);
        assertEquals(original.at(1), new Integer(actualMaximum));
    }

    private static <T> void assertHeapEquals(Heap<T> expected, Heap<T> actual) {
        assertEquals(expected.heapSize, actual.heapSize);
        assertArrayEquals(expected, actual);
    }

    @DataProvider
    public static Object[][] provideDataForExtractMax() {
        return new Object[][]{
                {new Array<>(34)},
                {new Array<>(3,2,1)},
                {new Array<>(3,1,2)},
                {new Array<>(16,14,10,8,7,9,3,2,4,1)},
                {new Array<>(27,7,20,4,6,13,17,0,3,2,1,5,11,10)}
        };
    }

    @Test
    @UseDataProvider("provideDataForExtractMax")
    public void shouldExtractMaximumFromMaxHeap(Array<Integer> array) {
        // given
        Heap<Integer> heap = new Heap<>(array);
        Heap<Integer> original = new Heap<>(array);

        // when
        int actualMaximum = Chapter6.heapExtractMax(heap);

        // then
        assertEquals(original.heapSize - 1, heap.heapSize);
        assertMaxHeap(heap);
        assertEquals(original.at(1), new Integer(actualMaximum));
        for (int i = 2; i <= original.heapSize; i++) {
            assertHeapContains(heap, original.at(i));
        }
    }

    private <T> void assertHeapContains(Heap<T> heap, T element) {
        boolean contains = false;
        for (int i = 1; i <= heap.heapSize; i++) {
            contains = contains || heap.at(i).equals(element);
        }
        assertTrue(contains);
    }

    @Test(expected = RuntimeException.class)
    public void shouldThrowExceptionWhenExtractingMaximumFromEmptyHeap() {
        // given

        // when
        Chapter6.heapExtractMax(new Heap<>());

        // then
    }

    @DataProvider
    public static Object[][] provideDataForIncreasingKeyInMaxHeap() {
        return new Object[][]{
                {new Array<>(34), 1, 34},
                {new Array<>(3,2,1), 2, 3},
                {new Array<>(3,2,1), 2, 2},
                {new Array<>(16,14,10,8,7,9,3,2,4,1), 10, 20},
                {new Array<>(27,7,20,4,6,13,17,0,3,2,1,5,11,10), 12, 23}
        };
    }

    @Test
    @UseDataProvider("provideDataForIncreasingKeyInMaxHeap")
    public void shouldIncreaseKeyInMaxHeap(Array<Integer> array, int position, int newKey) {
        // given
        Heap<Integer> heap = new Heap<>(array);
        Heap<Integer> original = new Heap<>(array);

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

        // when
        Chapter6.heapIncreaseKey(new Heap<>(new Array<>(16,14,10,8,7,9,3,2,4,1)), 3, 6);

        // then
    }

    @DataProvider
    public static Object[][] provideDataForInsertingIntoMaxHeap() {
        return new Object[][]{
                {new Array<>(), 34},
                {new Array<>(34), 43},
                {new Array<>(4,2,1), 3},
                {new Array<>(15,13,9,5,12,8,7,4,0,6,2,1), 10},
                {new Array<>(27,7,20,4,6,13,17,0,3,2,1,5,11,10), 34}
        };
    }

    @Test
    @UseDataProvider("provideDataForInsertingIntoMaxHeap")
    public void shouldInsertIntoMaxHeap(Array<Integer> array, int newKey) {
        // given
        Heap<Integer> heap = new Heap<>(array);
        Heap<Integer> original = new Heap<>(array);

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

    @DataProvider
    public static Object[][] provideDataForHeapMinimum() {
        return new Object[][]{
                {new Array<>(34)},
                {new Array<>(1,2,3)},
                {new Array<>(1,3,2)},
                {new Array<>(1,2,3,7,8,4,10,14,9,16)},
                {new Array<>(0,6,1,11,7,3,2,27,13,17,20,10,4,5)}
        };
    }

    @Test
    @UseDataProvider("provideDataForHeapMinimum")
    public void shouldGetMinimumFromMinHeap(Array<Integer> array) {
        // given
        Heap<Integer> heap = new Heap<>(array);
        Heap<Integer> original = new Heap<>(array);

        // when
        int actualMinimum = Chapter6.heapMinimum(heap);

        // then
        assertHeapEquals(original, heap);
        assertEquals(original.at(1), new Integer(actualMinimum));
    }

    @DataProvider
    public static Object[][] provideDataForExtractMin() {
        return new Object[][]{
                {new Array<>(34)},
                {new Array<>(1,2,3)},
                {new Array<>(1,3,2)},
                {new Array<>(1,2,3,7,8,4,10,14,9,16)},
                {new Array<>(0,6,1,11,7,3,2,27,13,17,20,10,4,5)}
        };
    }

    @Test
    @UseDataProvider("provideDataForExtractMin")
    public void shouldExtractMinimumFromMinHeap(Array<Integer> array) {
        // given
        Heap<Integer> heap = new Heap<>(array);
        Heap<Integer> original = new Heap<>(array);

        // when
        int actualMinimum = Chapter6.heapExtractMin(heap);

        // then
        assertEquals(original.heapSize - 1, heap.heapSize);
        assertMinHeap(heap);
        assertEquals(original.at(1), new Integer(actualMinimum));
        for (int i = 2; i <= original.heapSize; i++) {
            assertHeapContains(heap, original.at(i));
        }
    }

    @Test(expected = RuntimeException.class)
    public void shouldThrowExceptionWhenExtractingMinimumFromEmptyHeap() {
        // given

        // when
        Chapter6.heapExtractMin(new Heap<>());

        // then
    }

    @DataProvider
    public static Object[][] provideDataForDecreasingKeyInMinHeap() {
        return new Object[][]{
                {new Array<>(34), 1, 34},
                {new Array<>(1,2,3), 2, 1},
                {new Array<>(1,2,3), 2, 2},
                {new Array<>(1,2,3,7,8,4,10,14,9,16), 10, 0},
                {new Array<>(0,6,1,11,7,3,2,27,13,17,20,10,4,5), 11, 4}
        };
    }

    @Test
    @UseDataProvider("provideDataForDecreasingKeyInMinHeap")
    public void shouldDecreaseKeyInMinHeap(Array<Integer> array, int position, int newKey) {
        // given
        Heap<Integer> heap = new Heap<>(array);
        Heap<Integer> original = new Heap<>(array);

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

        // when
        Chapter6.heapDecreaseKey(new Heap<>(new Array<>(1,2,3,7,8,4,10,14,9,16)), 4, 12);

        // then
    }

    @DataProvider
    public static Object[][] provideDataForInsertingIntoMinHeap() {
        return new Object[][]{
                {new Array<>(), 34},
                {new Array<>(34), 23},
                {new Array<>(1,3,4), 2},
                {new Array<>(0,1,4,8,2,5,6,9,15,7,12,13), 3},
                {new Array<>(1,6,2,11,7,3,4,27,13,17,20,10,14,5), 0}
        };
    }

    @Test
    @UseDataProvider("provideDataForInsertingIntoMinHeap")
    public void shouldInsertIntoMinHeap(Array<Integer> array, int newKey) {
        // given
        Heap<Integer> heap = new Heap<>(array);
        Heap<Integer> original = new Heap<>(array);

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

    @DataProvider
    public static Object[][] provideDataForHeapDelete() {
        return new Object[][]{
                {new Array<>(34), 1},
                {new Array<>(2,1), 1},
                {new Array<>(3,2,1), 1},
                {new Array<>(3,2,1), 3},
                {new Array<>(27,7,20,4,6,13,17,0,3,2,1,5,11,10), 1},
                {new Array<>(27,7,20,4,6,13,17,0,3,2,1,5,11,10), 10},
                {new Array<>(3.14,-0.53,2.55,-1.54,-2.75,2.23), 5},
                {new Array<>("eee","ddd","ccc","aaa","bbb"), 1}
        };
    }

    @Test
    @UseDataProvider("provideDataForHeapDelete")
    public <T extends Comparable> void shouldDeleteFromMaxHeap(Array<T> array, int position) {
        // given
        Heap<T> heap = new Heap<>(array);
        Heap<T> original = new Heap<>(array);

        // when
        T actualKey = Chapter6.maxHeapDelete(heap, position);

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

    @DataProvider
    public static Object[][] provideDataForFifoQueueOperationsUsingPriorityQueue() {
        return new Object[][]{
                {new Array<>(34,null), new Array<>()},
                {new Array<>(4,1,3,null,8,null), new Array<>(3,8)},
                {new Array<>(3.14,-0.53,null,-1.54,null,2.23), new Array<>(-1.54,2.23)},
                {new Array<>("eee",null,"ccc","aaa",null), new Array<>("aaa")}
        };
    }

    @Test
    @UseDataProvider("provideDataForFifoQueueOperationsUsingPriorityQueue")
    public <T extends Comparable> void shouldPerformFifoQueueOperationsUsingPriorityQueue(Array<T> contents, Array<T> expectedContents) {
        // given
        Chapter6.PriorityQueueWithRanks<T> priorityQueue = new Chapter6.PriorityQueueWithRanks<>();

        // when
        for (int i = 1; i <= contents.length; i++) {
            if (contents.at(i) != null) {
                Chapter6.enqueueUsingPriorityQueue(priorityQueue, contents.at(i));
            } else {
                Chapter6.dequeueUsingPriorityQueue(priorityQueue);
            }
        }

        // then
        for (int i = 1; i <= expectedContents.length; i++) {
            assertEquals(expectedContents.at(i), Chapter6.dequeueUsingPriorityQueue(priorityQueue));
        }
        assertEquals(0, priorityQueue.heapSize);
    }

    @DataProvider
    public static Object[][] provideDataForStackOperationsUsingPriorityQueue() {
        return new Object[][]{
                {new Array<>(34,null), new Array<>()},
                {new Array<>(4,1,3,null,8,null), new Array<>(1,4)},
                {new Array<>(3.14,-0.53,null,-1.54,null,2.23), new Array<>(2.23,3.14)},
                {new Array<>("eee",null,"ccc","aaa",null), new Array<>("ccc")}
        };
    }

    @Test
    @UseDataProvider("provideDataForStackOperationsUsingPriorityQueue")
    public <T extends Comparable> void shouldPerformStackOperationsUsingPriorityQueue(Array<T> contents, Array<T> expectedContents) {
        // given
        Chapter6.PriorityQueueWithRanks<T> priorityQueue = new Chapter6.PriorityQueueWithRanks<>();

        // when
        for (int i = 1; i <= contents.length; i++) {
            if (contents.at(i) != null) {
                Chapter6.pushUsingPriorityQueue(priorityQueue, contents.at(i));
            } else {
                Chapter6.popUsingPriorityQueue(priorityQueue);
            }
        }

        // then
        for (int i = 1; i <= expectedContents.length; i++) {
            assertEquals(expectedContents.at(i), Chapter6.popUsingPriorityQueue(priorityQueue));
        }
        assertEquals(0, priorityQueue.heapSize);
    }

    @DataProvider
    public static Object[][] provideDataForBuildingMaxHeapUsingBuildMaxHeap_() {
        return new Object[][]{
                {new Array<>(34)},
                {new Array<>(2,1)},
                {new Array<>(1,2,3)},
                {new Array<>(4,0,1,3,2)},
                {new Array<>(20,4,0,13,5,17,1,2,6,10,7,27,11,3)}
        };
    }

    @Test
    @UseDataProvider("provideDataForBuildingMaxHeapUsingBuildMaxHeap_")
    public void shouldBuildMaxHeapUsingBuildMaxHeap_(Array<Integer> array) {
        // given
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

    @DataProvider
    public static Object[][] provideDataForDAryMaxHeapify() {
        return new Object[][]{
                {new Array<>(34), 3, 1},
                {new Array<>(2,1,3,4), 3, 1},
                {new Array<>(4,1,2,3), 3, 1},
                {new Array<>(6,14,10,9,7,12,3,2,8,1), 3, 1},
                {new Array<>(21,17,27,13,20,16,8,9,13,18,23,24,5,3,12,7), 4, 1}
        };
    }

    @Test
    @UseDataProvider("provideDataForDAryMaxHeapify")
    public void shouldRestoreMaxDAryHeapProperty(Array<Integer> array, int d, int position) {
        // given
        Heap<Integer> heap = new Heap<>(array);
        Heap<Integer> original = new Heap<>(array);

        // when
        Chapter6.dAryMaxHeapify(heap, d, position);

        // then
        assertShuffled(original, heap);
        assertEquals(original.length, heap.length);
        assertDAryMaxHeap(heap, d);
    }

    private void assertDAryMaxHeap(Heap<Integer> heap, int d) {
        for (int i = 2; i <= heap.heapSize; i++) {
            assertTrue(geq(heap.at(dAryParent(d, i)), heap.at(i)));
        }
    }

    @DataProvider
    public static Object[][] provideDataForInsertingToDAryMaxHeap() {
        return new Object[][]{
                {new Array<>(), 3, 34},
                {new Array<>(34), 3, 43},
                {new Array<>(4,1,2,3), 3, 0},
                {new Array<>(17,16,10,9,7,14,3,2,8,1), 3, 20},
                {new Array<>(32,17,27,13,20,16,8,9,13,18,23,24,5,3,12,7), 4, 35}
        };
    }

    @Test
    @UseDataProvider("provideDataForInsertingToDAryMaxHeap")
    public void shouldInsertToDAryMaxHeap(Array<Integer> array, int d, int newKey) {
        // given
        Heap<Integer> heap = new Heap<>(array);
        Heap<Integer> original = new Heap<>(array);

        // when
        Chapter6.dAryMaxHeapInsert(heap, d, newKey);

        // then
        assertEquals(original.heapSize + 1, heap.heapSize);
        assertDAryMaxHeap(heap, d);
        assertHeapContains(heap, newKey);
        for (int i = 1; i <= original.heapSize; i++) {
            assertHeapContains(heap, original.at(i));
        }
    }

    @DataProvider
    public static Object[][] provideDataForIncreasingKeyInDAryMapHeap() {
        return new Object[][]{
                {new Array<>(34), 3, 1, 43},
                {new Array<>(2,3,1), 3, 3, 4},
                {new Array<>(3,2,1), 3, 3, 1},
                {new Array<>(17,16,10,9,7,14,3,2,8,1), 3, 5, 20},
                {new Array<>(32,17,27,13,20,16,8,9,13,18,23,24,5,3,12,7), 4, 14, 35}
        };
    }

    @Test
    @UseDataProvider("provideDataForIncreasingKeyInDAryMapHeap")
    public void shouldIncreaseKeyInDAryMaxHeap(Array<Integer> array, int d, int position, int newKey) {
        // given
        Heap<Integer> heap = new Heap<>(array);
        Heap<Integer> original = new Heap<>(array);

        // when
        Chapter6.dAryHeapIncreaseKey(heap, d, position, newKey);

        // then
        assertEquals(original.heapSize, heap.heapSize);
        assertDAryMaxHeap(heap, d);
        assertHeapContains(heap, newKey);
        for (int i = 1; i <= original.heapSize; i++) {
            if (i != position) {
                assertHeapContains(heap, original.at(i));
            }
        }
    }

    @DataProvider
    public static Object[][] provideDataForYoungSort() {
        return new Object[][]{
                {new Array<>(34)},
                {new Array<>(4,3,2,1)},
                {new Array<>(1,3,4,2)},
                {new Array<>(5,7,9,2,6,8,6,6,3,1,7,8,6,3,7,8)},
                {new Array<>(1,2,3,3,5,6,6,6,6,7,7,7,8,8,8,9)}
        };
    }

    @Test
    @UseDataProvider("provideDataForYoungSort")
    public void shouldSortArrayUsingYoungSort(Array<Integer> array) {
        // given
        Array<Integer> original = new Array<>(array);

        // when
        Chapter6.youngSort(array);

        // then
        assertShuffled(original, array);
        assertSorted(array);
    }

    @DataProvider
    public static Object[][] provideDataForSuccessfulYoungSearch() {
        return new Object[][]{
                {new Array[] {
                        new Array<>(34)
                }, 34},
                {new Array[] {
                        new Array<>(2,5),
                        new Array<>(4,Integer.MAX_VALUE)
                }, 4},
                {new Array[] {
                        new Array<>(2,Integer.MAX_VALUE),
                        new Array<>(Integer.MAX_VALUE,Integer.MAX_VALUE)
                }, 2},
                {new Array[] {
                        new Array<>(2,3,14,16),
                        new Array<>(4,8,Integer.MAX_VALUE,Integer.MAX_VALUE),
                        new Array<>(5,12,Integer.MAX_VALUE,Integer.MAX_VALUE),
                        new Array<>(9,Integer.MAX_VALUE,Integer.MAX_VALUE,Integer.MAX_VALUE)
                }, 5},
                {new Array[] {
                        new Array<>(2,3,14,16),
                        new Array<>(4,8,Integer.MAX_VALUE,Integer.MAX_VALUE),
                        new Array<>(5,12,Integer.MAX_VALUE,Integer.MAX_VALUE),
                        new Array<>(9,Integer.MAX_VALUE,Integer.MAX_VALUE,Integer.MAX_VALUE)
                }, 16}
        };
    }

    @Test
    @UseDataProvider("provideDataForSuccessfulYoungSearch")
    public void shouldFindKeyUsingYoungSearch(Array<Integer>[] rows, int key) {
        // given
        Young Y = new Young(rows);

        // when
        boolean keyFound = Chapter6.youngSearch(Y, Y.rows, Y.columns, key);

        // then
        assertTrue(keyFound);
    }

    @DataProvider
    public static Object[][] provideDataForUnsuccessfulYoungSearch() {
        return new Object[][]{
                {new Array[] {
                        new Array<>(34)
                }, 35},
                {new Array[] {
                        new Array<>(Integer.MAX_VALUE)
                }, 35},
                {new Array[] {
                        new Array<>(2,5),
                        new Array<>(4,Integer.MAX_VALUE)
                }, 3},
                {new Array[] {
                        new Array<>(2,Integer.MAX_VALUE),
                        new Array<>(Integer.MAX_VALUE,Integer.MAX_VALUE)
                }, 1},
                {new Array[] {
                        new Array<>(2,3,14,16),
                        new Array<>(4,8,Integer.MAX_VALUE,Integer.MAX_VALUE),
                        new Array<>(5,12,Integer.MAX_VALUE,Integer.MAX_VALUE),
                        new Array<>(9,Integer.MAX_VALUE,Integer.MAX_VALUE,Integer.MAX_VALUE)
                }, 10},
                {new Array[] {
                        new Array<>(2,3,14,16),
                        new Array<>(4,8,Integer.MAX_VALUE,Integer.MAX_VALUE),
                        new Array<>(5,12,Integer.MAX_VALUE,Integer.MAX_VALUE),
                        new Array<>(9,Integer.MAX_VALUE,Integer.MAX_VALUE,Integer.MAX_VALUE)
                }, 17}
        };
    }

    @Test
    @UseDataProvider("provideDataForUnsuccessfulYoungSearch")
    public void shouldNotFindKeyUsingYoungSearch(Array<Integer>[] rows, int key) {
        // given
        Young Y = new Young(rows);

        // when
        boolean keyFound = Chapter6.youngSearch(Y, Y.rows, Y.columns, key);

        // then
        assertFalse(keyFound);
    }

}
