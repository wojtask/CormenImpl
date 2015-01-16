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
import static pl.kwojtas.cormenimpl.TestUtil.assertArrayEquals;

@RunWith(DataProviderRunner.class)
public class Chapter6Test {

    @DataProvider
    public static Object[][] provideDataForHeapsort() {
        return new Object[][]{
                {new Array<>(34), new Array<>(34)},
                {new Array<>(3,2,1), new Array<>(1,2,3)},
                {new Array<>(5,7,9,2,6,8,6,6,3,1,7,8), new Array<>(1,2,3,5,6,6,6,7,7,8,8,9)},
                {new Array<>(1,2,3,5,6,6,6,7,7,8,8,9), new Array<>(1,2,3,5,6,6,6,7,7,8,8,9)},
                {new Array<>(3.14,-2.75,-0.53,2.55,2.23), new Array<>(-2.75,-0.53,2.23,2.55,3.14)},
                {new Array<>("aaa","eee","ccc","ddd","bbb"), new Array<>("aaa","bbb","ccc","ddd","eee")}
        };
    }

    @Test
    @UseDataProvider("provideDataForHeapsort")
    public <T extends Comparable> void shouldSortArrayUsingHeapsort(Array<T> array, Array<T> expectedSorted) {
        // given

        // when
        Chapter6.heapsort(array);

        // then
        assertArrayEquals(expectedSorted, array);
    }

    @DataProvider
    public static Object[][] provideDataForMinHeapify() {
        return new Object[][]{
                {new Array<>(34), 1, new Array<>(34)},
                {new Array<>(2,1,3), 1, new Array<>(1,2,3)},
                {new Array<>(1,2,3), 1, new Array<>(1,2,3)},
                {new Array<>(0,1,16,3,4,7,17,12,10,5,13,9,8,27), 3, new Array<>(0,1,7,3,4,8,17,12,10,5,13,9,16,27)},
                {new Array<>(1,9,3,2,8,4,10,14,7,16), 2, new Array<>(1,2,3,7,8,4,10,14,9,16)},
                {new Array<>(-2.75,3.14,2.55,-0.53,2.23), 2, new Array<>(-2.75,-0.53,2.55,3.14,2.23)},
                {new Array<>("eee","aaa","ccc","bbb","ddd"), 1, new Array<>("aaa","bbb","ccc","eee","ddd")}
        };
    }

    @Test
    @UseDataProvider("provideDataForMinHeapify")
    public <T extends Comparable> void shouldRestoreMinHeapPropertyUsingMinHeapify(Array<T> array, int position, Array<T> expectedHeapContents) {
        // given
        Heap<T> A = new Heap<>(array);
        Heap<T> expectedHeap = new Heap<>(expectedHeapContents);

        // when
        Chapter6.minHeapify(A, position);

        // then
        assertHeapEquals(expectedHeap, A);
    }

    @DataProvider
    public static Object[][] provideDataForMaxHeapify() {
        return new Object[][]{
                {new Array<>(34), 1, new Array<>(34)},
                {new Array<>(2,3,1), 1, new Array<>(3,2,1)},
                {new Array<>(3,2,1), 1, new Array<>(3,2,1)},
                {new Array<>(16,4,10,14,7,9,3,2,8,1), 2, new Array<>(16,14,10,8,7,9,3,2,4,1)},
                {new Array<>(27,17,3,16,13,10,1,5,7,12,4,8,9,0), 3, new Array<>(27,17,10,16,13,9,1,5,7,12,4,8,3,0)},
                {new Array<>(3.14,-2.75,-0.53,2.55,2.23), 2, new Array<>(3.14,2.55,-0.53,-2.75,2.23)},
                {new Array<>("aaa","eee","ccc","ddd","bbb"), 1, new Array<>("eee","ddd","ccc","aaa","bbb")}
        };
    }

    @Test
    @UseDataProvider("provideDataForMaxHeapify")
    public <T extends Comparable> void shouldRestoreMaxHeapPropertyUsingIterativeMaxHeapify(Array<T> array, int position, Array<T> expectedHeapContents) {
        // given
        Heap<T> A = new Heap<>(array);
        Heap<T> expectedHeap = new Heap<>(expectedHeapContents);

        // when
        Chapter6.iterativeMaxHeapify(A, position);

        // then
        assertHeapEquals(expectedHeap, A);
    }

    @DataProvider
    public static Object[][] provideDataForHeapMaximum() {
        return new Object[][]{
                {new Array<>(34), 34},
                {new Array<>(3,2,1), 3},
                {new Array<>(3,1,2), 3},
                {new Array<>(16,14,10,8,7,9,3,2,4,1), 16},
                {new Array<>(27,7,20,4,6,13,17,0,3,2,1,5,11,10), 27}
        };
    }

    @Test
    @UseDataProvider("provideDataForHeapMaximum")
    public void shouldGetMaximumFromMaxHeap(Array<Integer> array, int expectedMaximum) {
        // given
        Heap<Integer> A = new Heap<>(array);
        Heap<Integer> original = new Heap<>(A);

        // when
        int actualMaximum = Chapter6.heapMaximum(A);

        // then
        assertEquals(expectedMaximum, actualMaximum);
        assertHeapEquals(original, A);
    }

    @DataProvider
    public static Object[][] provideDataForExtractMax() {
        return new Object[][]{
                {new Array<>(34), 34, new Array<>(34)},
                {new Array<>(3,2,1), 3, new Array<>(2,1,1)},
                {new Array<>(3,1,2), 3, new Array<>(2,1,2)},
                {new Array<>(16,14,10,8,7,9,3,2,4,1), 16, new Array<>(14,8,10,4,7,9,3,2,1,1)},
                {new Array<>(27,7,20,4,6,13,17,0,3,2,1,5,11,10), 27, new Array<>(20,7,17,4,6,13,10,0,3,2,1,5,11,10)}
        };
    }

    @Test
    @UseDataProvider("provideDataForExtractMax")
    public void shouldExtractMaximumFromMaxHeap(Array<Integer> array, int expectedMaximum, Array<Integer> expectedHeapContents) {
        // given
        Heap<Integer> A = new Heap<>(array);
        Heap<Integer> expectedHeap = new Heap<>(expectedHeapContents);
        expectedHeap.heapSize--;

        // when
        int actualMaximum = Chapter6.heapExtractMax(A);

        // then
        assertEquals(expectedMaximum, actualMaximum);
        assertHeapEquals(expectedHeap, A);
    }

    @Test(expected = RuntimeException.class)
    public void shouldThrowExceptionWhenExtractingMaximumFromEmptyHeap() {
        // given
        Heap<Integer> A = new Heap<>();

        // when
        Chapter6.heapExtractMax(A);

        // then
    }

    @DataProvider
    public static Object[][] provideDataForIncreasingKeyInMaxHeap() {
        return new Object[][]{
                {new Array<>(34), 1, 34, new Array<>(34)},
                {new Array<>(3,2,1), 2, 3, new Array<>(3,3,1)},
                {new Array<>(3,2,1), 2, 2, new Array<>(3,2,1)},
                {new Array<>(16,14,10,8,7,9,3,2,4,1), 10, 20, new Array<>(20,16,10,8,14,9,3,2,4,7)},
                {new Array<>(27,7,20,4,6,13,17,0,3,2,1,5,11,10), 12, 23, new Array<>(27,7,23,4,6,20,17,0,3,2,1,13,11,10)}
        };
    }

    @Test
    @UseDataProvider("provideDataForIncreasingKeyInMaxHeap")
    public void shouldIncreaseKeyInMaxHeap(Array<Integer> array, int position, int newKey, Array<Integer> expectedHeapContents) {
        // given
        Heap<Integer> A = new Heap<>(array);
        Heap<Integer> expectedHeap = new Heap<>(expectedHeapContents);

        // when
        Chapter6.heapIncreaseKey(A, position, newKey);

        // then
        assertHeapEquals(expectedHeap, A);
    }

    @Test(expected = RuntimeException.class)
    public void shouldThrowExceptionWhenIncreasingKeyInMaxHeapWithSmallerNumber() {
        // given
        Array<Integer> array = new Array<>(16,14,10,8,7,9,3,2,4,1);
        Heap<Integer> A = new Heap<>(array);

        // when
        Chapter6.heapIncreaseKey(A, 3, 6);

        // then
    }

    @DataProvider
    public static Object[][] provideDataForInsertingIntoMaxHeap() {
        return new Object[][]{
                {new Array<>(), 34, new Array<>(34)},
                {new Array<>(34), 43, new Array<>(43,34)},
                {new Array<>(4,2,1), 3, new Array<>(4,3,1,2)},
                {new Array<>(15,13,9,5,12,8,7,4,0,6,2,1), 10, new Array<>(15,13,10,5,12,9,7,4,0,6,2,1,8)},
                {new Array<>(27,7,20,4,6,13,17,0,3,2,1,5,11,10), 34, new Array<>(34,7,27,4,6,13,20,0,3,2,1,5,11,10,17)}
        };
    }

    @Test
    @UseDataProvider("provideDataForInsertingIntoMaxHeap")
    public void shouldInsertIntoMaxHeap(Array<Integer> array, int newKey, Array<Integer> expectedHeapContents) {
        // given
        Heap<Integer> A = new Heap<>(array);
        Heap<Integer> expectedHeap = new Heap<>(expectedHeapContents);

        // when
        Chapter6.maxHeapInsert(A, newKey);

        // then
        assertHeapEquals(expectedHeap, A);
    }

    @DataProvider
    public static Object[][] provideDataForHeapMinimum() {
        return new Object[][]{
                {new Array<>(34), 34},
                {new Array<>(1,2,3), 1},
                {new Array<>(1,3,2), 1},
                {new Array<>(1,2,3,7,8,4,10,14,9,16), 1},
                {new Array<>(0,6,1,11,7,3,2,27,13,17,20,10,4,5), 0}
        };
    }

    @Test
    @UseDataProvider("provideDataForHeapMinimum")
    public void shouldGetMinimumFromMinHeap(Array<Integer> array, int expectedMinimum) {
        // given
        Heap<Integer> A = new Heap<>(array);
        Heap<Integer> original = new Heap<>(A);

        // when
        int actualMinimum = Chapter6.heapMinimum(A);

        // then
        assertEquals(expectedMinimum, actualMinimum);
        assertHeapEquals(original, A);
    }

    @DataProvider
    public static Object[][] provideDataForExtractMin() {
        return new Object[][]{
                {new Array<>(34), 34, new Array<>(34)},
                {new Array<>(1,2,3), 1, new Array<>(2,3,3)},
                {new Array<>(1,3,2), 1, new Array<>(2,3,2)},
                {new Array<>(1,2,3,7,8,4,10,14,9,16), 1, new Array<>(2,7,3,9,8,4,10,14,16,16)},
                {new Array<>(0,6,1,11,7,3,2,27,13,17,20,10,4,5), 0, new Array<>(1,6,2,11,7,3,5,27,13,17,20,10,4,5)}
        };
    }

    @Test
    @UseDataProvider("provideDataForExtractMin")
    public void shouldExtractMinimumFromMinHeap(Array<Integer> array, int expectedMinimum, Array<Integer> expectedHeapContents) {
        // given
        Heap<Integer> A = new Heap<>(array);
        Heap<Integer> expectedHeap = new Heap<>(expectedHeapContents);
        expectedHeap.heapSize--;

        // when
        int actualMinimum = Chapter6.heapExtractMin(A);

        // then
        assertEquals(expectedMinimum, actualMinimum);
        assertHeapEquals(expectedHeap, A);
    }

    @Test(expected = RuntimeException.class)
    public void shouldThrowExceptionWhenExtractingMinimumFromEmptyHeap() {
        // given
        Heap<Integer> A = new Heap<>();

        // when
        Chapter6.heapExtractMin(A);

        // then
    }

    @DataProvider
    public static Object[][] provideDataForDecreasingKeyInMinHeap() {
        return new Object[][]{
                {new Array<>(34), 1, 34, new Array<>(34)},
                {new Array<>(1,2,3), 2, 1, new Array<>(1,1,3)},
                {new Array<>(1,2,3), 2, 2, new Array<>(1,2,3)},
                {new Array<>(1,2,3,7,8,4,10,14,9,16), 10, 0, new Array<>(0,1,3,7,2,4,10,14,9,8)},
                {new Array<>(0,6,1,11,7,3,2,27,13,17,20,10,4,5), 11, 4, new Array<>(0,4,1,11,6,3,2,27,13,17,7,10,4,5)}
        };
    }

    @Test
    @UseDataProvider("provideDataForDecreasingKeyInMinHeap")
    public void shouldDecreaseKeyInMinHeap(Array<Integer> array, int position, int newKey, Array<Integer> expectedHeapContents) {
        // given
        Heap<Integer> A = new Heap<>(array);
        Heap<Integer> expectedHeap = new Heap<>(expectedHeapContents);

        // when
        Chapter6.heapDecreaseKey(A, position, newKey);

        // then
        assertHeapEquals(expectedHeap, A);
    }

    @Test(expected = RuntimeException.class)
    public void shouldThrowExceptionWhenDecreasingKeyInMinHeapWithLargerNumber() {
        // given
        Array<Integer> array = new Array<>(1,2,3,7,8,4,10,14,9,16);
        Heap<Integer> A = new Heap<>(array);

        // when
        Chapter6.heapDecreaseKey(A, 4, 12);

        // then
    }

    @DataProvider
    public static Object[][] provideDataForInsertingIntoMinHeap() {
        return new Object[][]{
                {new Array<>(), 34, new Array<>(34)},
                {new Array<>(34), 23, new Array<>(23,34)},
                {new Array<>(1,3,4), 2, new Array<>(1,2,4,3)},
                {new Array<>(0,1,4,8,2,5,6,9,15,7,12,13), 3, new Array<>(0,1,3,8,2,4,6,9,15,7,12,13,5)},
                {new Array<>(1,6,2,11,7,3,4,27,13,17,20,10,14,5), 0, new Array<>(0,6,1,11,7,3,2,27,13,17,20,10,14,5,4)}
        };
    }

    @Test
    @UseDataProvider("provideDataForInsertingIntoMinHeap")
    public void shouldInsertIntoMinHeap(Array<Integer> array, int newKey, Array<Integer> expectedHeapContents) {
        // given
        Heap<Integer> A = new Heap<>(array);
        Heap<Integer> expectedHeap = new Heap<>(expectedHeapContents);

        // when
        Chapter6.minHeapInsert(A, newKey);

        // then
        assertHeapEquals(expectedHeap, A);
    }

    @DataProvider
    public static Object[][] provideDataForHeapDelete() {
        return new Object[][]{
                {new Array<>(34), 1, new Array<>(34), 34},
                {new Array<>(2,1), 1, new Array<>(1,2), 2},
                {new Array<>(3,2,1), 1, new Array<>(2,1,3), 3},
                {new Array<>(3,2,1), 3, new Array<>(3,2,1), 1},
                {new Array<>(27,7,20,4,6,13,17,0,3,2,1,5,11,10), 1, new Array<>(20,7,17,4,6,13,10,0,3,2,1,5,11,27), 27},
                {new Array<>(27,7,20,4,6,13,17,0,3,2,1,5,11,10), 10, new Array<>(27,10,20,4,7,13,17,0,3,6,1,5,11,2), 2},
                {new Array<>(3.14,-0.53,2.55,-1.54,-2.75,2.23), 5, new Array<>(3.14,2.23,2.55,-1.54,-0.53,-2.75), -2.75},
                {new Array<>("eee","ddd","ccc","aaa","bbb"), 1, new Array<>("ddd","bbb","ccc","aaa","eee"), "eee"}
        };
    }

    @Test
    @UseDataProvider("provideDataForHeapDelete")
    public <T extends Comparable> void shouldDeleteFromMaxHeap(Array<T> array, int position, Array<T> expectedHeapContents, T expectedKey) {
        // given
        Heap<T> A = new Heap<>(array);
        Heap<T> expectedHeap = new Heap<>(expectedHeapContents);
        expectedHeap.heapSize--;

        // when
        T actualKey = Chapter6.maxHeapDelete(A, position);

        // then
        assertHeapEquals(expectedHeap, A);
        assertEquals(expectedKey, actualKey);
    }

    @DataProvider
    public static Object[][] provideDataForBuildingMaxHeapUsingBuildMaxHeap_() {
        return new Object[][]{
                {new Array<>(34), new Array<>(34)},
                {new Array<>(2,1), new Array<>(2,1)},
                {new Array<>(1,2,3), new Array<>(3,1,2)},
                {new Array<>(4,0,1,3,2), new Array<>(4,3,1,0,2)},
                {new Array<>(20,4,0,13,5,17,1,2,6,10,7,27,11,3), new Array<>(27,13,20,6,10,17,3,2,4,5,7,0,11,1)}
        };
    }

    @Test
    @UseDataProvider("provideDataForBuildingMaxHeapUsingBuildMaxHeap_")
    public void shouldBuildMaxHeapUsingBuildMaxHeap_(Array<Integer> array, Array<Integer> expectedHeapContents) {
        // given
        Heap<Integer> expectedHeap = new Heap<>(expectedHeapContents);

        // when
        Heap<Integer> actualHeap = Chapter6.buildMaxHeap_(array);

        // then
        assertHeapEquals(expectedHeap, actualHeap);
    }

    @DataProvider
    public static Object[][] provideDataForDAryMaxHeapify() {
        return new Object[][]{
                {new Array<>(34), 3, 1, new Array<>(34)},
                {new Array<>(2,1,3,4), 3, 1, new Array<>(4,1,3,2)},
                {new Array<>(4,1,2,3), 3, 1, new Array<>(4,1,2,3)},
                {new Array<>(6,14,10,9,7,12,3,2,8,1), 3, 1, new Array<>(14,12,10,9,7,6,3,2,8,1)},
                {new Array<>(21,17,27,13,20,16,8,9,13,18,23,24,5,3,12,7), 4, 1, new Array<>(27,17,24,13,20,16,8,9,13,18,23,21,5,3,12,7)}
        };
    }

    @Test
    @UseDataProvider("provideDataForDAryMaxHeapify")
    public void shouldRestoreMaxDAryHeapProperty(Array<Integer> array, int d, int position, Array<Integer> expectedHeapContents) {
        // given
        Heap<Integer> A = new Heap<>(array);
        Heap<Integer> expectedHeap = new Heap<>(expectedHeapContents);

        // when
        Chapter6.dAryMaxHeapify(A, d, position);

        // then
        assertHeapEquals(expectedHeap, A);
    }

    @DataProvider
    public static Object[][] provideDataForInsertingToDAryMaxHeap() {
        return new Object[][]{
                {new Array<>(), 3, 34, new Array<>(34)},
                {new Array<>(34), 3, 43, new Array<>(43,34)},
                {new Array<>(4,1,2,3), 3, 0, new Array<>(4,1,2,3,0)},
                {new Array<>(17,16,10,9,7,14,3,2,8,1), 3, 20, new Array<>(20,16,10,17,7,14,3,2,8,1,9)},
                {new Array<>(32,17,27,13,20,16,8,9,13,18,23,24,5,3,12,7), 4, 35, new Array<>(35,17,27,32,20,16,8,9,13,18,23,24,5,3,12,7,13)}
        };
    }

    @Test
    @UseDataProvider("provideDataForInsertingToDAryMaxHeap")
    public void shouldInsertToDAryMaxHeap(Array<Integer> array, int d, int key, Array<Integer> expectedHeapContents) {
        // given
        Heap<Integer> A = new Heap<>(array);
        Heap<Integer> expectedHeap = new Heap<>(expectedHeapContents);

        // when
        Chapter6.dAryMaxHeapInsert(A, d, key);

        // then
        assertHeapEquals(expectedHeap, A);
    }

    @DataProvider
    public static Object[][] provideDataForIncreasingKeyInDAryMapHeap() {
        return new Object[][]{
                {new Array<>(34), 3, 1, 43, new Array<>(43)},
                {new Array<>(2,3,1), 3, 3, 4, new Array<>(4,3,2)},
                {new Array<>(3,2,1), 3, 3, 1, new Array<>(3,2,1)},
                {new Array<>(17,16,10,9,7,14,3,2,8,1), 3, 5, 20, new Array<>(20,17,10,9,16,14,3,2,8,1)},
                {new Array<>(32,17,27,13,20,16,8,9,13,18,23,24,5,3,12,7), 4, 14, 35, new Array<>(35,17,27,32,20,16,8,9,13,18,23,24,5,13,12,7)}
        };
    }

    @Test
    @UseDataProvider("provideDataForIncreasingKeyInDAryMapHeap")
    public void shouldIncreaseKeyInDAryMaxHeap(Array<Integer> array, int d, int position, int newKey, Array<Integer> expectedHeapContents) {
        // given
        Heap<Integer> A = new Heap<>(array);
        Heap<Integer> expectedHeap = new Heap<>(expectedHeapContents);

        // when
        Chapter6.dAryHeapIncreaseKey(A, d, position, newKey);

        // then
        assertHeapEquals(expectedHeap, A);
    }

    @DataProvider
    public static Object[][] provideDataForYoungSort() {
        return new Object[][]{
                {new Array<>(34), new Array<>(34)},
                {new Array<>(4,3,2,1), new Array<>(1,2,3,4)},
                {new Array<>(1,3,4,2), new Array<>(1,2,3,4)},
                {new Array<>(5,7,9,2,6,8,6,6,3,1,7,8,6,3,7,8), new Array<>(1,2,3,3,5,6,6,6,6,7,7,7,8,8,8,9)},
                {new Array<>(1,2,3,3,5,6,6,6,6,7,7,7,8,8,8,9), new Array<>(1,2,3,3,5,6,6,6,6,7,7,7,8,8,8,9)}
        };
    }

    @Test
    @UseDataProvider("provideDataForYoungSort")
    public void shouldSortArrayUsingYoungSort(Array<Integer> array, Array<Integer> expectedSorted) {
        // given

        // when
        Chapter6.youngSort(array);

        // then
        assertArrayEquals(expectedSorted, array);
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

    private static <T> void assertHeapEquals(Heap<T> expected, Heap<T> actual) {
        assertEquals(expected.heapSize, actual.heapSize);
        assertArrayEquals(expected, actual);
    }

}
